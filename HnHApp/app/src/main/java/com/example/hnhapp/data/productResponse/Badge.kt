package com.example.hnhapp.data.productResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnhapp.database.ProductDAO
import com.google.gson.annotations.SerializedName
//@Entity(tableName = ProductDAO.DATA_BADGE_NAME)
data class Badge(
    //@PrimaryKey
    //val id:Int = 0,
    @SerializedName("value")
    val value: String,
    @SerializedName("color")
    val color: String
)
