package com.example.hnhapp.data

import com.example.hnhapp.data.requestModel.LoginResponse
import com.example.hnhapp.data.requestModel.RequestLogin
import com.example.hnhapp.data.responseModel.BaseResponse
import retrofit2.http.Body
import retrofit2.http.PUT

interface HnHService {

    @PUT("user/signin")
    suspend fun login( @Body requestLogin: RequestLogin):BaseResponse<LoginResponse>

}