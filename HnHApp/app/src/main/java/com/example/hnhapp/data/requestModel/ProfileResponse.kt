package com.example.hnhapp.data.requestModel

import com.google.gson.annotations.SerializedName

class ProfileResponse (
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("occupation") val occupation: String,
    @SerializedName("avatarId") val avatarId: String
)