package com.example.hnhapp.data.repository

import com.example.hnhapp.data.HnHService
import com.example.hnhapp.data.requestModel.LoginResponse
import com.example.hnhapp.data.requestModel.RequestLogin
import javax.inject.Inject

class HnHRepositoryImpl @Inject constructor (
    private val service:HnHService
){
    suspend fun login(login: String, password: String): LoginResponse =
        service.login(RequestLogin(login = login, password = password)).data
}