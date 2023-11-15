package com.example.hnhapp.data.requestModel

import com.google.gson.annotations.SerializedName

class LoginResponse (
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("profile") val profile: ProfileResponse
)