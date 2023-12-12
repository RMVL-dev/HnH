package com.example.hnhapp.presentation.accountSettings

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hnhapp.BuildConfig
import com.example.hnhapp.MainActivity
import com.example.hnhapp.R
import com.example.hnhapp.databinding.FragmentAccountSettingsBinding
import com.example.hnhapp.presentation.accountSettings.bottomSheet.BottomSheet
import com.example.hnhapp.utils.cameraAction
import com.example.hnhapp.utils.occupationList
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AccountSettingsFragment : Fragment() {

    private var _binding: FragmentAccountSettingsBinding? = null
    private val binding get() = _binding!!

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        uri?.let {
            binding.profileImage.setImageURI(it)
            binding.icCameraPlaceholder.visibility = View.GONE
        }
    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()){isSuccess ->
        if (isSuccess){
            _latestUri?.let {
                binding.profileImage.setImageURI(it)
                binding.icCameraPlaceholder.visibility = View.GONE
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    private var _latestUri: Uri? = null
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
        binding.settingBtChange.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    /**
     * создание BottomSheet
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
                if (cameraAction[position] == "Выбрать из галереи")
                    selectImageFromGallery()
                else if (cameraAction[position] == "Сделать снимок")
                    checkPermissionsForUseCamera()
                bottomSheet.dismiss()
            }
        }
        bottomSheet.show(requireActivity().supportFragmentManager,"tag")
    }

    /**
     * Проверка на полученное разрешения для доступа к камере
     */
    private fun checkPermissionsForUseCamera() {

        if (ActivityCompat.checkSelfPermission(
                requireActivity().baseContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePicture()
        }else{
            requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    /**
     * Создание временного файла для снимка и запуск контракта для получения URI снимка
     */
    private fun takePicture(){
        lifecycleScope.launch {
            val file = withContext(Dispatchers.IO) {
                File.createTempFile(
                    "tmp_account_image",
                    ".png",
                    this@AccountSettingsFragment.context?.cacheDir
                )
            }.apply {
                createNewFile()
                deleteOnExit()
            }

            FileProvider
                .getUriForFile(requireActivity().applicationContext, "${BuildConfig.APPLICATION_ID}.provider",file)
                .let { uri ->
                _latestUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    /**
     * Запуск контракта для обращения к галерее
     */
    private fun selectImageFromGallery(){
        selectImageFromGalleryResult.launch("image/*")
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
        _latestUri = null
        super.onDestroyView()
    }
}