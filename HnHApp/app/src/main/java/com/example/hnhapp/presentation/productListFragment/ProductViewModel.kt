package com.example.hnhapp.presentation.productListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.productResponse.Size
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.dataBase.converter.EntityTypeConverter
import com.example.hnhapp.dataBase.testEntitys.MainEntity
import com.example.hnhapp.domain.productUseCase.ProductUseCase
import com.example.hnhapp.utils.getError
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductViewModel @Inject constructor(
    private val useCase:ProductUseCase,
):ViewModel() {

    private val _productLiveData = MutableLiveData<ResponseState<List<Product>>>()
    val productData: LiveData<ResponseState<List<Product>>> = _productLiveData

    private val _size = MutableLiveData<Size>()
    val size: LiveData<Size> = _size

    private fun getProductList(){
        viewModelScope.launch {
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
            insertItems()
            return@launch cancel()
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
            if (_productLiveData.value is ResponseState.Success){
                return@launch cancel()
            }
            val list = mutableListOf<Product>()
            _productLiveData.value = ResponseState.Loading()
            useCase.getAllProducts().collect{dbList->
                for (entity in dbList){
                    list.add(entity.product)
                }
                if (list.isEmpty()) {
                    getProductList()
                } else {
                    _productLiveData.value = ResponseState.Success(data = list)
                }
            }
        }
    }

    fun getProductByPosition(position:Int):Product? =
        if (productData.value is ResponseState.Success)
                (productData.value as ResponseState.Success).data[position]
        else
            null

    fun getProductToOrderJson(product: Product):String?{
        var productToJson:Product = product
        size.value?.let {
            productToJson = product.copy(sizes = listOf(it))
        }
        return  EntityTypeConverter().productEntityToJson(product = productToJson)
    }

    fun setSize(size: Size){
        _size.value = size
    }

}