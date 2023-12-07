package com.example.hnhapp.presentation.accountSettings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hnhapp.R
import com.example.hnhapp.databinding.FragmentAccountSettingsBinding
import com.example.hnhapp.presentation.accountSettings.bottomSheet.BottomSheet
import com.example.hnhapp.utils.cameraAction
import com.example.hnhapp.utils.occupationList
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
    ): View {
        _binding = FragmentAccountSettingsBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initToolbar()
        isEmpty(binding.etSettingsFirstname)
        isEmpty(binding.etSettingsLastname)
        isEmpty(binding.etSettingsOtherOccupation)
        binding.etSettingsOccupation.setOnClickListener {
            createBottomSheet(list = occupationList)
        }
        binding.profileImage.setOnClickListener {
            createBottomSheet(list = cameraAction)
        }
    }

    /**
     * Проверка на пустоту полей имя, фамилия, другой род деятельности
     */
    private fun isEmpty(view: View){
        when(view.id){
            binding.etSettingsFirstname.id  -> {
                binding.etSettingsFirstname.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrEmpty()){
                        binding.tilSettingsFirstname.error = getString(R.string.error_sign_in)
                    }else{
                        binding.tilSettingsFirstname.error = null
                    }
                }
            }
            binding.etSettingsLastname.id -> {
                binding.etSettingsLastname.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrEmpty()){
                        binding.tilSettingsLastname.error = getString(R.string.error_sign_in)
                    }else{
                        binding.tilSettingsLastname.error = null
                    }
                }
            }
            binding.etSettingsOtherOccupation.id -> {
                binding.etSettingsOtherOccupation.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrEmpty()){
                        binding.tilSettingsOtherOccupation.error = getString(R.string.error_sign_in)
                    }else {
                        binding.tilSettingsOtherOccupation.error = null
                    }
                }
            }
            else -> {}
        }
    }


    /**
     * создание Bottom sheet
     */
    private fun createBottomSheet(list: List<String>){
        val bottomSheet = BottomSheet(list = list)
        if (list.hashCode() == occupationList.hashCode()){
            bottomSheet.setOnClick {position ->
                if (occupationList[position] == "Другое")
                    binding.tilSettingsOtherOccupation.visibility = View.VISIBLE
                else
                    binding.tilSettingsOtherOccupation.visibility = View.GONE
                binding.etSettingsOccupation.setText(occupationList[position])
                bottomSheet.dismiss()
            }
        }else if (list.hashCode() == cameraAction.hashCode()){
            bottomSheet.setOnClick {position ->
                bottomSheet.dismiss()
            }
        }
        bottomSheet.show(requireActivity().supportFragmentManager,"tag")
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