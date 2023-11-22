package com.example.hnhapp.presentation.productListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.dataBase.testEntitys.MainEntity
import com.example.hnhapp.domain.productUseCase.ProductUseCase
import com.example.hnhapp.utils.getError
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val useCase:ProductUseCase,
):ViewModel() {

    private val _productLiveData = MutableLiveData<ResponseState<List<Product>>>()
    val productData: LiveData<ResponseState<List<Product>>> = _productLiveData

    private fun getProductList(){
        viewModelScope.launch {
            _productLiveData.value = try {
                insertItems()
                ResponseState.Success(
                    data = useCase.getProductList()
                )
            }catch (e:Exception){
                ResponseState.Error(
                    e = e,
                    message = e.getError()
                )
            }
        }
    }

    private fun insertItems(){
        viewModelScope.launch {
            if (productData.value is ResponseState.Success) {
                for (product in (productData.value as ResponseState.Success).data){
                    useCase.insertItem(
                        entity = MainEntity(product = product)
                    )
                }
            }
        }
    }

    fun getAllItems(){
        viewModelScope.launch {
            val list = mutableListOf<Product>()
            _productLiveData.value = ResponseState.Loading()
            useCase.getAllProducts().collect{dbList->
                for (entity in dbList){
                    list.add(entity.product)
                }
                if (list.isEmpty())
                    getProductList()
                else
                    _productLiveData.value = ResponseState.Success(data = list)
            }
        }
    }


}