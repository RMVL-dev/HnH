package com.example.hnhapp.presentation.productListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnhapp.data.productResponse.Badge
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.productResponse.Size
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.dataBase.TestEntity
import com.example.hnhapp.dataBase.testEntitys.BadgeEntity
import com.example.hnhapp.dataBase.testEntitys.MainEntity
import com.example.hnhapp.dataBase.testEntitys.ProductEntity
import com.example.hnhapp.dataBase.testEntitys.SizeEntity
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
                entity = convertProduct(product)
            )
        }
    }


    private fun convertProduct(product: Product):MainEntity{
        return MainEntity(
            product = ProductEntity(
                id = product.id,
                title = product.title,
                department = product.department,
                price = product.price,
                badge = convertBadgeToEntity(product.badge),
                previewImageUrl = product.previewImageUrl,
                images = product.images,
                sizes = convertSizeToEntity(product.sizes),
                description = product.description,
                details = product.details
            )
        )
    }

    private fun convertBadgeToEntity(list: List<Badge>):List<BadgeEntity>{
        val entityBadge = mutableListOf<BadgeEntity>()
        repeat (list.size){i->
            entityBadge.add(
                BadgeEntity(
                    value = list[i].value,
                    color = list[i].color
                )
            )
        }
        return entityBadge
    }

    private fun convertSizeToEntity(list: List<Size>):List<SizeEntity>{
        val entityBadge = mutableListOf<SizeEntity>()
        repeat (list.size){i->
            entityBadge.add(
                SizeEntity(
                    value = list[i].value,
                    isAvailable = list[i].isAvailable
                )
            )
        }
        return entityBadge
    }
}