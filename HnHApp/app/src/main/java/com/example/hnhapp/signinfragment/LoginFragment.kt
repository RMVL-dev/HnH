package com.example.hnhapp.signinfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.example.hnhapp.R
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment() {
    @Inject
    lateinit var loginViewModelFactory: ViewModelProvider.Factory

    private val signInViewModel:SignInViewModel by createViewModelLazy(
        SignInViewModel::class,
        {this.viewModelStore},
        factoryProducer = {loginViewModelFactory}
    )

    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //validation for empty
        binding.etPassword.doOnTextChanged { text, start, before, count ->

            if (text!!.isNullOrEmpty()){
                binding.tilPassword.error = getString(R.string.error_sign_in)
                binding.vgBtSignInAction.buttonState(enable = false)
            }else{
                binding.tilPassword.error = null
                binding.vgBtSignInAction.buttonState(enable = true)
            }
        }

        binding.etLogin.doOnTextChanged { text, start, before, count ->
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
            if (text!!.matches(emailPattern) && text.isNotEmpty()){
                binding.tilLogin.error = null
                binding.vgBtSignInAction.buttonState(enable = true)
            }
            else{
                binding.tilLogin.error = getString(R.string.error_sign_in)
                binding.vgBtSignInAction.buttonState(enable = false)

            }
        }


        signInViewModel.loginLiveData.observe(viewLifecycleOwner){ value ->
            when(value){
                is ResponseState.Loading -> {
                    binding.vgBtSignInAction.loading()
                }
                is ResponseState.Error -> {
                    val snackbar = Snackbar
                        .make(view, "${value.error}", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.error_sign_in))
                        .setTextColor(resources.getColor(R.color.seashell))
                    snackbar.show()
                    binding.vgBtSignInAction.otherStates()
                }
                else -> {
                    binding.vgBtSignInAction.otherStates()
                }
            }
        }

        binding.vgBtSignInAction.login {
            signInViewModel.login(
                login = binding.etLogin.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}