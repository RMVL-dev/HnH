package com.example.hnhapp.presentation.orderFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.hnhapp.R
import com.example.hnhapp.databinding.FragmentOrderBinding
import com.example.hnhapp.presentation.contracts.MapActivityContract
import com.example.hnhapp.utils.getFormattedCurrency
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class OrderFragment : Fragment() {

    private var _binding:FragmentOrderBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var orderViewModelFactory: ViewModelProvider.Factory

    private val orderViewModel:OrderViewModel by createViewModelLazy(
        OrderViewModel::class,
        {this.viewModelStore},
        factoryProducer = {orderViewModelFactory}
    )

    private val arg:OrderFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel.convertProduct(product = arg.product)
        orderViewModel.product.observe(viewLifecycleOwner){product ->
            Glide.with(binding.orderItemImage)
                .load(product.previewImageUrl)
                .error(R.drawable.error_image)
                .into(binding.orderItemImage)
            binding.orderItemTitle.text = "${product.sizes[0].value} • ${product.title}"
            binding.orderItemDepartment.text = product.department
            binding.btBuyNow.text = "Купить за ${getFormattedCurrency(product.price)}"
        }

        orderViewModel.counterOrderItems.observe(viewLifecycleOwner){counter ->
            binding.counter.setCountedValue(counter = counter)
        }
        val activityLauncher = registerForActivityResult(MapActivityContract()){result ->
            binding.etHouseAddress.setText(result)
        }
        binding.tilHouseAddress.setEndIconOnClickListener {
            activityLauncher.launch("What is address?")
        }

        binding.counter.setIncrease { orderViewModel.increaseOrderCounter() }
        binding.counter.setDecrease { orderViewModel.decreaseOrderCounter() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}