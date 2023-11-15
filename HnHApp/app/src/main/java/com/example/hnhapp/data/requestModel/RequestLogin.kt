package com.example.hnhapp.data.requestModel

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("Email") val login:String,
    @SerializedName("Password") val password:String
)
