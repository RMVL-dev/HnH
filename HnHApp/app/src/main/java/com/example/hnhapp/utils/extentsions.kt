package com.example.hnhapp.utils

import android.graphics.Color
import android.view.View
import com.example.hnhapp.R
import com.example.hnhapp.data.responseModel.ErrorBaseResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.HttpException


fun View.settingSnackBar(message:String, colorId:Int): Snackbar =
    Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .setBackgroundTint(context.resources.getColor(colorId))
        .setTextColor(context.resources.getColor(R.color.seashell))


fun Exception.getError():String? =
    if (this is HttpException){
        Gson().fromJson(response()?.errorBody()?.string(), ErrorBaseResponse::class.java).error.message
    }else{
        message
    }