package com.example.hnhapp.presentation.lesson4Fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.hnhapp.R
import com.example.hnhapp.databinding.FragmentLesson4Binding
import com.example.hnhapp.presentation.signinfragment.SignInViewModel

class Lesson4Fragment : Fragment() {

    //private val viewModel:SignInViewModel by viewModels()

    private var _binding: FragmentLesson4Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLesson4Binding.inflate(inflater, container, false)
        //binding.tvLesson4.text = viewModel.someVariable.value
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding.btLesson4.setOnClickListener {
        //    viewModel.changeSomeVariable(text = binding.etLesson4.text.toString())
        //    binding.tvLesson4.text = viewModel.someVariable.value
        //}

        //TODO: Задать вопрос как сделать чтобы ACTION_DONE распознававлся
        //binding.etLesson4.setOnKeyListener { view, keyCode, keyEvent ->
        //    if (keyEvent.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.FLAG_EDITOR_ACTION )
        //    {
        //        viewModel.changeSomeVariable(text = binding.etLesson4.text.toString())
        //        binding.tvLesson4.text = viewModel.someVariable.value
        //        true
        //    }else{
        //        false
        //    }
        //}

        //binding.vgButton

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}