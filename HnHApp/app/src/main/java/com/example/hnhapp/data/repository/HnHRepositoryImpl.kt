package com.example.hnhapp.data.repository

import com.example.hnhapp.data.HnHService
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.requestModel.LoginResponse
import com.example.hnhapp.data.requestModel.RequestLogin
import com.example.hnhapp.dataBase.TestEntity
import com.example.hnhapp.dataBase.TestProductDao
import com.example.hnhapp.dataBase.testEntitys.MainEntity
import javax.inject.Inject

class HnHRepositoryImpl @Inject constructor (
    private val service:HnHService,
    private val dataSource: TestProductDao
){
    suspend fun login(login: String, password: String): LoginResponse =
        service.login(RequestLogin(login = login, password = password)).data

    suspend fun getProductList(): List<Product> =
        service.getProductList().data


    //suspend fun insertItem(entity: TestEntity) =
    //    dataSource.addItem(entity)

    suspend fun insertItem(entity: MainEntity) =
        dataSource.addItem(entity)
}