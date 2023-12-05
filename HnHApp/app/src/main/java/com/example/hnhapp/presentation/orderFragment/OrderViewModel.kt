package com.example.hnhapp.presentation.orderFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnhapp.data.order.request.OrderRequestProduct
import com.example.hnhapp.data.order.request.OrderRequest
import com.example.hnhapp.data.order.response.OrderResponse
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.responseModel.ResponseState
import com.example.hnhapp.dataBase.converter.EntityTypeConverter
import com.example.hnhapp.domain.orderUseCase.OrderUseCase
import com.example.hnhapp.utils.formatDate
import com.example.hnhapp.utils.getError
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val orderUseCase:OrderUseCase
) :ViewModel() {

    private var _counterOrderItems = MutableLiveData<Int>()
    val counterOrderItems:LiveData<Int> get() = _counterOrderItems

    private var _product = MutableLiveData<Product>()
    val product:LiveData<Product> get() = _product

    private var _orderResponseState = MutableLiveData<ResponseState<OrderResponse>>()
    val orderResponseState:LiveData<ResponseState<OrderResponse>> get() = _orderResponseState

    private var calendar:Calendar = Calendar.getInstance()
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

    /**
     * Преобразование из json в продукт
     */
    fun convertProduct(product: String?){
        _product.value = EntityTypeConverter().productJsonToEntity(product)
    }

    /**
     * Преобразование в тип нужный для запроса
     */
    private fun createOrderEntity(
        house: String,
        apartment: String
    ): OrderRequest {
        val orderProduct = if (_product.value != null) {
            counterOrderItems.value?.let { quantity ->
                _product.value?.let { product ->
                    OrderRequestProduct(
                        productId = product.id,
                        size = product.sizes.first().value,
                        quantity = quantity
                    )
                }
            }
        } else {
            OrderRequestProduct(
                productId = "",
                size = "",
                quantity = 0
            )
        }

        return OrderRequest(
            house = house,
            apartment = apartment,
            dateDelivery = formatDate(calendar = calendar, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
            products = listOf(orderProduct)
        )
    }


    /**
     * Запрос
     */
    fun checkToOrder(
        house:String,
        apartment: String,
    ){
        val order = createOrderEntity(
            house = house,
            apartment = apartment
        )
        viewModelScope.launch {
            _orderResponseState.value = ResponseState.Loading()
            _orderResponseState.value = try {
                ResponseState.Success(
                    data = orderUseCase.order(orderRequest = order)
                )
            }catch (e:Exception){
                ResponseState.Error(
                    e = e,
                    message = e.getError()
                )
            }
        }
    }

    /**
     * установка даты доставки
     */
    fun setCalendar(calendar: Calendar){
        this.calendar = calendar
    }
}