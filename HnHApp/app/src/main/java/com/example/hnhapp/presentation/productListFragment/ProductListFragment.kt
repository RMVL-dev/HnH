package com.example.hnhapp.presentation.productListFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.databinding.FragmentProductListBinding
import com.example.hnhapp.presentation.productItemFragment.bottomSheet.SizesBottomSheet
import com.example.hnhapp.presentation.productListFragment.adapter.ProductAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var productViewModelFactory: ViewModelProvider.Factory

    private val productViewModel:ProductViewModel by createViewModelLazy(
        ProductViewModel::class,
        {this.viewModelStore},
        factoryProducer = {productViewModelFactory}
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        productViewModel.getAllItems()
        setActionToAccountButton()
        productViewModel.productData.observe(viewLifecycleOwner){value ->
            when(value){
                is ResponseState.Error -> {
                    errorOrEmptyState()
                    value.message?.let {
                        binding.errorScreen.setErrorState(
                            click = { productViewModel.getAllItems() },
                            errorMes = it
                        )
                    }
                }
                is ResponseState.Loading -> {
                    binding.rvProducts.visibility = View.GONE
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.errorScreen.setOkState()
                }
                is ResponseState.Success -> {
                    if (value.data.isNotEmpty()) {
                        initRecyclerView(data = value.data)
                        binding.progressCircular.visibility = View.GONE
                        binding.errorScreen.setOkState()
                    }else{
                        errorOrEmptyState()
                        binding.errorScreen.setErrorState {
                            productViewModel.getAllItems()
                        }
                    }
                }
            }
        }
    }

    /**
     * Установка навигации в настройки аккаунта
     */
    private fun setActionToAccountButton(){
        binding.accountButton.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_accountSettingsFragment)
        }
    }


    private fun errorOrEmptyState(){
        binding.rvProducts.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
    }



    private fun getAdapter(data:List<Product>):ProductAdapter {
        val adapter = ProductAdapter(data)
        adapter.setOnClick { position ->
            val product = data[position]
            btBuyClickAction(product = product)
        }
        adapter.onCardClick {position ->
            findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToProductItemFragment(position))
        }
        return adapter
    }

    private fun btBuyClickAction(product: Product){
        val bottomSheet =  SizesBottomSheet(listSizes = product.sizes)
        bottomSheet.setOnclick {sizePosition ->
            val size = product.sizes[sizePosition]
            productViewModel.setSize(size)
            val jsonProduct = productViewModel.getProductToOrderJson(product = product)
            findNavController().navigate(
                ProductListFragmentDirections
                    .actionProductListFragmentToOrderFragment(
                        product = jsonProduct
                    )
            )
            bottomSheet.dismiss()
        }
        bottomSheet.show(requireActivity().supportFragmentManager,"tag")
    }


    private fun initRecyclerView(data:List<Product>){
        binding.rvProducts.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        binding.rvProducts.adapter = getAdapter(data)
        binding.rvProducts.visibility = View.VISIBLE
    }




    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}