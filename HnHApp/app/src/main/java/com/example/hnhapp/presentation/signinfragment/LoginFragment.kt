package com.example.hnhapp.presentation.signinfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hnhapp.R
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.databinding.FragmentLoginBinding
import com.example.hnhapp.utils.settingSnackBar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment() {
    @Inject
    lateinit var loginViewModelFactory: ViewModelProvider.Factory

    private val signInViewModel: SignInViewModel by createViewModelLazy(
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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //validation for empty
        binding.etPassword.doOnTextChanged { text, start, before, count ->

            if (text.isNullOrEmpty()){
                binding.tilPassword.error = getString(R.string.error_sign_in)
                binding.vgBtSignInAction.buttonState(enable = false)
            }else{
                binding.tilPassword.error = null
                binding.vgBtSignInAction.buttonState(enable = true)
            }
        }

        binding.etLogin.doOnTextChanged { text, start, before, count ->
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
            if (text.isNullOrEmpty()){
                binding.tilLogin.error = getString(R.string.error_sign_in)
                binding.vgBtSignInAction.buttonState(enable = false)
            }else{
                if (text.matches(emailPattern)){
                    binding.tilLogin.error = null
                    binding.vgBtSignInAction.buttonState(enable = true)
                }else{
                    binding.tilLogin.error = getString(R.string.error_sign_in)
                    binding.vgBtSignInAction.buttonState(enable = false)
                }
            }
        }


        signInViewModel.loginLiveData.observe(viewLifecycleOwner){ value ->
            when(value){
                is ResponseState.Loading -> {
                    binding.vgBtSignInAction.loading()
                }
                is ResponseState.Error -> {
                    value.message?.let {
                        view.settingSnackBar(
                            message = it,
                            colorId = R.color.error_sign_in
                        ).show()
                    }
                    binding.vgBtSignInAction.otherStates(buttonTextId = R.string.sign_in)
                }
                is ResponseState.Success -> {
                    binding.vgBtSignInAction.otherStates(buttonTextId = R.string.sign_in)
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToProductListFragment()
                    )
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