package com.example.hnhapp.domain.usecase

import com.example.hnhapp.data.repository.HnHRepositoryImpl
import com.example.hnhapp.data.repository.SharedPreferencesReq
import com.example.hnhapp.data.requestModel.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: HnHRepositoryImpl,
    private val sharedPreferences: SharedPreferencesReq
){

    suspend fun execute(login:String, password: String):LoginResponse{
        val loginData = repository.login(login = login, password = password)
        sharedPreferences.userToken = loginData.accessToken
        return loginData
    }
}