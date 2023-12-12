package com.example.hnhapp.presentation.productItemFragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.doOnAttach
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.databinding.FragmentProductItemBinding
import com.example.hnhapp.presentation.productItemFragment.bottomSheet.SizesBottomSheet
import com.example.hnhapp.presentation.productListFragment.ProductViewModel
import com.example.hnhapp.utils.convertListToStringWithBullets
import com.example.hnhapp.utils.getFormattedCurrency
import com.example.hnhapp.utils.settingSnackBar
import dagger.android.support.AndroidSupportInjection
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductItemBinding.inflate(inflater, container, false)
        binding.etProductSize.doOnAttach {
            it.requestFocus()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = productViewModel.getProductByPosition(arg.product)

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
            binding.etProductSize.setOnClickListener {
                customClickToEditText(product = productItem)
            }
            binding.tilProductSize.setEndIconOnClickListener{
                customClickToEditText(product = productItem)
            }
        }

        binding.btProductBuyNow.setOnClickListener {
            if (product != null &&
                binding.etProductSize.text?.isNotEmpty() == true
            ) {
                val jsonProduct = productViewModel.getProductToOrderJson(product = product)
                findNavController().navigate(
                    ProductItemFragmentDirections.actionProductItemFragmentToOrderFragment(
                        product = jsonProduct
                    )
                )
            }else if(binding.etProductSize.text?.isEmpty() == true){
                view.settingSnackBar("Не выбран размер", R.color.error_sign_in).show()
            }else{
                view.settingSnackBar("Не выбран товар", R.color.error_sign_in).show()
            }
        }
    }

    /**
     * кастомное нажатие на EditText
     */
    private fun customClickToEditText(product:Product){
        val bottomSheet =  SizesBottomSheet(listSizes = product.sizes)
        bottomSheet.setOnclick {position ->
            val size = product.sizes[position]
            binding.etProductSize.setText(size.value)
            productViewModel.setSize(size)
            binding.etProductSize.setText(product.sizes[position].value)
            bottomSheet.dismiss()
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