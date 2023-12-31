package com.example.hnhapp.data.repository

import com.example.hnhapp.data.HnHService
import com.example.hnhapp.data.order.request.OrderRequest
import com.example.hnhapp.data.order.response.OrderResponse
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.requestModel.LoginResponse
import com.example.hnhapp.data.requestModel.RequestLogin
import com.example.hnhapp.dataBase.ProductDao
import com.example.hnhapp.dataBase.testEntitys.MainEntity
import javax.inject.Inject

class HnHRepositoryImpl @Inject constructor (
    private val service:HnHService,
    private val dataSource: ProductDao
){
    suspend fun login(login: String, password: String): LoginResponse =
        service.login(RequestLogin(login = login, password = password)).data

    suspend fun getProductList(): List<Product> =
        service.getProductList().data

    suspend fun insertItem(entity: MainEntity) =
        dataSource.addItem(entity)

    fun getAllProductsFromDB() =
        dataSource.getProducts()

    suspend fun order(orderRequest: OrderRequest): OrderResponse =
        service.order(order = orderRequest).data
}