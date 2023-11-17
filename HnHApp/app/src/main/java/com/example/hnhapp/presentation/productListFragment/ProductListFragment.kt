package com.example.hnhapp.presentation.productListFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.databinding.FragmentProductListBinding
import com.example.hnhapp.presentation.productListFragment.adapter.ProductAdapter
import com.example.hnhapp.utils.settingSnackBar
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

        productViewModel.getProductList()
        productViewModel.productData.observe(viewLifecycleOwner){value ->
            when(value){
                is ResponseState.Error -> {
                    value.message?.let {
                        view.settingSnackBar(
                            message = it
                        ).show()
                    }
                    binding.rvProducts.visibility = View.GONE
                }
                is ResponseState.Loading -> {
                    binding.rvProducts.visibility = View.GONE
                }
                is ResponseState.Success -> {
                    binding.rvProducts.addItemDecoration(DividerItemDecoration(requireContext(),RecyclerView.VERTICAL))
                    val adapter = ProductAdapter(value.data)
                    adapter.setOnClick {
                        Toast.makeText(requireContext(), "BUY button clicked!", Toast.LENGTH_LONG).show()
                    }
                    binding.rvProducts.adapter = adapter
                    binding.rvProducts.visibility = View.VISIBLE
                }
            }
        }


    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}