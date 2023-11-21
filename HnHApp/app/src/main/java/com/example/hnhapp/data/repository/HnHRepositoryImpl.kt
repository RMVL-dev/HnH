package com.example.hnhapp.data.repository

import com.example.hnhapp.data.HnHService
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.requestModel.LoginResponse
import com.example.hnhapp.data.requestModel.RequestLogin
import com.example.hnhapp.database.ProductDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HnHRepositoryImpl @Inject constructor (
    private val service:HnHService,
    private val dataAccessObject:ProductDAO
){
    suspend fun login(login: String, password: String): LoginResponse =
        service.login(RequestLogin(login = login, password = password)).data

    suspend fun getProductList(): List<Product> =
        service.getProductList().data

    suspend fun getProductFromDBs(): Flow<List<Product>> =
        dataAccessObject.getAllProducts()

    suspend fun addProductsToDBs(product: Product) =
        dataAccessObject.addProducts(productEntity = product)
}