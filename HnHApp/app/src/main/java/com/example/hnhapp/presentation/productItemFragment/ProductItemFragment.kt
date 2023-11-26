package com.example.hnhapp.presentation.productItemFragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.databinding.FragmentProductItemBinding
import com.example.hnhapp.presentation.productListFragment.ProductViewModel
import com.example.hnhapp.utils.convertListToStringWithBullets
import com.example.hnhapp.utils.getFormattedCurrency
import dagger.android.support.AndroidSupportInjection
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class ProductItemFragment : Fragment() {

    @Inject
    lateinit var productViewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by createViewModelLazy(
        ProductViewModel::class,
        {this.viewModelStore},
        factoryProducer = {productViewModelFactory}
    )

    private val arg:ProductItemFragmentArgs by navArgs()

    private var _binding:FragmentProductItemBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = productViewModel.getProductByPosition(arg.product)

        product?.let { product ->
            initToolBar(title = product.title)
            binding.productItemPrice.text = getFormattedCurrency(product.price)
            binding.productBestSeller.visibility = if (arg.product%2 == 0) View.VISIBLE else View.GONE //чтобы не каждый был хит сезона :)
            binding.productItemDepartment.text = product.department
            binding.productDescription.text = product.description
            //binding.productMaterials.text = product.details.toString() //TODO туть лист стрингов нужно в буллит сделать
            binding.productMaterials.text = convertListToStringWithBullets(list = product.details) //todo нужно что-то с буллитами сделать
        }

        //todo ну тут помелочи осталось сделать карусель, обработку экранов ну типа чтобы и ошибка тоже была да и вроде все


    }


    private fun initToolBar(title:String){
        binding.productItemToolbar
            .setupWithNavController(
                findNavController()
            )
        binding.productItemToolbar.title = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}