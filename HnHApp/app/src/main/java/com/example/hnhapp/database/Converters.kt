package com.example.hnhapp.database

import androidx.room.TypeConverter
import com.example.hnhapp.data.productResponse.Badge
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.productResponse.Size
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

class Converters {

    private val gson by lazy { Gson() }

    @TypeConverter
    fun listToItem(list: List<Product>?):String? =
        if (list == null)
            null
        else
            try {
                gson.toJson(list)
            }catch (e:Exception) {
                null
            }

    @TypeConverter
    fun jsonToList(value: String?): List<Product>? {
        if (value == null)
            return null
        val listType = object : TypeToken<List<Product>?>() {}.type
        return try {
            gson.fromJson(value, listType)
        }catch (e:Exception){
            emptyList()
        }
    }


    @TypeConverter
    fun badgeListToItem(list: List<Badge>):String? =
        if (list == null)
            null
        else
            try {
                gson.toJson(list)
            }catch (e:Exception) {
                null
            }

    @TypeConverter
    fun badgeJsonToList(value: String?): List<Badge>? {
        if (value == null)
            return null

        val listType = object : TypeToken<List<Badge>?>() {}.type

        return try {
            gson.fromJson(value, listType)
        }catch (e:Exception){
            emptyList()
        }
    }


    @TypeConverter
    fun sizeListToItem(list: List<Size>?):String? =
        if (list == null)
            null
        else
            try {
                gson.toJson(list)
            }catch (e:Exception) {
                null
            }

    @TypeConverter
    fun sizeJsonToList(value: String?): List<Size>? {
        if (value == null)
            return null

        val listType = object : TypeToken<List<Size>?>() {}.type

        return try {
            gson.fromJson(value, listType)
        }catch (e:Exception){
            emptyList()
        }
    }

    @TypeConverter
    fun stringListToItem(list: List<String>?):String? =
        if (list == null)
            null
        else
            try {
                gson.toJson(list)
            }catch (e:Exception) {
                null
            }

    @TypeConverter
    fun stringJsonToList(value: String?): List<String>? {
        if (value == null)
            return null

        val listType = object : TypeToken<List<String>?>() {}.type

        return try {
            gson.fromJson(value, listType)
        }catch (e:Exception){
            emptyList()
        }
    }


}