package com.example.hnhapp.dataBase.converter

import androidx.room.TypeConverter
import com.example.hnhapp.dataBase.testEntitys.ProductEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TestEntityTypeConverter {

    private val gson by lazy { Gson() }

    @TypeConverter
    fun productEntityToJson(productEntity: ProductEntity?):String? =
        if (productEntity == null)
            null
        else try {
            gson.toJson(productEntity)
        }catch (e:Exception){
            null
        }

    @TypeConverter
    fun productJsonToEntity(value:String?): ProductEntity? {
        if (value == null)
            return null

        val typeEntity = object : TypeToken<ProductEntity?>() {}.type

        return try {
            gson.fromJson(value,typeEntity)
        }catch (e:Exception){
            null
        }
    }


}