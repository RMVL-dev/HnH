package com.example.hnhapp.data.productResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnhapp.database.ProductDAO
import com.google.gson.annotations.SerializedName
//@Entity(tableName = ProductDAO.DATA_SIZE_NAME)
data class Size(
    //@PrimaryKey
    //val id:Int = 0,
    @SerializedName("value")
    val value:String,
    @SerializedName("isAvailable")
    val isAvailable: Boolean
)
