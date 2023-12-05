package com.example.hnhapp.data

import com.example.hnhapp.data.order.request.OrderRequest
import com.example.hnhapp.data.order.response.OrderResponse
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.requestModel.LoginResponse
import com.example.hnhapp.data.requestModel.RequestLogin
import com.example.hnhapp.data.responseModel.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface HnHService {

    @PUT("user/signin")
    suspend fun login( @Body requestLogin: RequestLogin):BaseResponse<LoginResponse>

    @GET("products")
    suspend fun getProductList():BaseResponse<List<Product>>

    @POST("orders/checkout")
    suspend fun order(@Body order: OrderRequest):BaseResponse<OrderResponse>

}