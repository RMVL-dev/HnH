package com.example.hnhapp.data.responseModel

import com.google.gson.annotations.SerializedName

class ErrorBaseResponse(
    @SerializedName("error") val error: ErrorMessageResponse
)


class ErrorMessageResponse(
    @SerializedName("message") val message: String
)