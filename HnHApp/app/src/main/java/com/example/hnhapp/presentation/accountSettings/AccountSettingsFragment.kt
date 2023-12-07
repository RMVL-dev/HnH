package com.example.hnhapp.presentation.accountSettings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hnhapp.R
import com.example.hnhapp.databinding.FragmentAccountSettingsBinding
import dagger.android.support.AndroidSupportInjection

class AccountSettingsFragment : Fragment() {

    private var _binding: FragmentAccountSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSettingsBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initToolbar()
    }

    /**
     * Инициализация тул бара
     */
    private fun initToolbar(){
        binding.settingsToolbar.setupWithNavController(
            findNavController()
        )
        binding.settingsToolbar.title = ""
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}