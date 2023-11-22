package com.example.hnhapp.presentation.productListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.dataBase.testEntitys.MainEntity
import com.example.hnhapp.domain.productUseCase.ProductUseCase
import com.example.hnhapp.utils.getError
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val useCase:ProductUseCase,
):ViewModel() {

    private val _productLiveData = MutableLiveData<ResponseState<List<Product>>>()
    val productData: LiveData<ResponseState<List<Product>>> = _productLiveData

    fun getProductList(){
        viewModelScope.launch {
            _productLiveData.value = ResponseState.Loading()
            _productLiveData.value = try {
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

    fun insertItem(product: Product){
        viewModelScope.launch {
            useCase.insertItem(
                entity = MainEntity(product = product)
            )
        }
    }



}