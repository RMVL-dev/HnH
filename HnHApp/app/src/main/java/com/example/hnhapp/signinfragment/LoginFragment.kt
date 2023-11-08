package com.example.hnhapp.signinfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.hnhapp.R
import com.example.hnhapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Test colors on error mode
        binding.etPassword.doOnTextChanged { text, start, before, count ->

            if (text!!.length > 1){
                binding.tilPassword.error = getString(R.string.error_sign_in)
            }else{
                binding.tilPassword.error = null
            }
        }

        //Test colors on error mode
        binding.etLogin.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 1){
                binding.tilLogin.error = getString(R.string.error_sign_in)
            }else{
                binding.tilLogin.error = null
            }
        }

        binding.btSignInAction.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_lesson4Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}