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

    /**
     * Сделал вью модель синглтоном, знаю не очень хорошее решение, но я не придумал лучше :(
     */
    @Inject
    lateinit var productViewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by createViewModelLazy(
        ProductViewModel::class,
        {this.viewModelStore},
        factoryProducer = {productViewModelFactory}
    )

    /**
     * передаю позицию нужного продукта в списке продуктов
     */
    private val arg:ProductItemFragmentArgs by navArgs()

    private var _binding:FragmentProductItemBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = productViewModel.getProductByPosition(arg.product)

        /* тут не вижу смысла все разносить по методам, потому что
        * просто много элементов на экране нужно иницилизировать
        * ну и вроде метод меньше экрана поэтому читать его должно быть удобно :)
         */
        product?.let { productItem ->
            initToolBar(title = productItem.title)
            binding.productItemTitle.text = productItem.title
            binding.productItemPrice.text = getFormattedCurrency(productItem.price)
            binding.productBestSeller.visibility = if (arg.product%2 == 0) View.VISIBLE else View.GONE //чтобы не каждый был хит сезона :)
            binding.productItemDepartment.text = productItem.department
            binding.productDescription.text = productItem.description
            binding.productMaterials.text = convertListToStringWithBullets(list = productItem.details) //todo нужно спросить про spannable String
            binding.productImages.setProduct(product = product)
            binding.productImages.onClick()
            binding.tilProductSize.setEndIconOnClickListener{
                customClickToEditText(product = productItem)
            }
        }

    }

    /**
     * кастомное нажатие на EditText
     */
    private fun customClickToEditText(product:Product){
        val bottomSheet =  SizesBottomSheet(listSizes = product.sizes)
        bottomSheet.setOnclick {position ->
            binding.etProductSize.setText(product.sizes[position].value)
        }
        bottomSheet.show(requireActivity().supportFragmentManager,"tag")
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