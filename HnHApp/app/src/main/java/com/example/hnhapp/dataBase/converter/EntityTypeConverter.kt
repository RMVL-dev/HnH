package com.example.hnhapp.dataBase.converter

import androidx.room.TypeConverter
import com.example.hnhapp.data.productResponse.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EntityTypeConverter {

    private val gson by lazy { Gson() }

    @TypeConverter
    fun productEntityToJson(product: Product?):String? =
        if (product == null)
            null
        else try {
            gson.toJson(product)
        }catch (e:Exception){
            null
        }

    @TypeConverter
    fun productJsonToEntity(value:String?): Product? {
        if (value == null)
            return null

        val typeEntity = object : TypeToken<Product?>() {}.type

        return try {
            gson.fromJson(value,typeEntity)
        }catch (e:Exception){
            null
        }
    }


}