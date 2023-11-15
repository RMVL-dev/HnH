package com.example.hnhapp.data.responseModel

import com.google.gson.annotations.SerializedName

class BaseResponse<T> (
    @SerializedName("data") val data: T
)