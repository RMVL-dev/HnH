package com.example.hnhapp.presentation.orderFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.dataBase.converter.EntityTypeConverter
import com.example.hnhapp.domain.orderUseCase.OrderUseCase
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val order:OrderUseCase
) :ViewModel() {

    private var _counterOrderItems = MutableLiveData<Int>()
    val counterOrderItems:LiveData<Int> get() = _counterOrderItems

    private var _product = MutableLiveData<Product>()
    val product:LiveData<Product> get() = _product

    init {
        _counterOrderItems.value = 1
    }

    fun increaseOrderCounter(){
        _counterOrderItems.value = _counterOrderItems.value?.inc()
    }

    fun decreaseOrderCounter(){
        _counterOrderItems.value?.let {
            if (it > 1)
                _counterOrderItems.value = _counterOrderItems.value?.dec()
        }
    }

    fun convertProduct(product: String?){
        _product.value = EntityTypeConverter().productJsonToEntity(product)
    }
}