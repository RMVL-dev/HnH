package com.example.hnhapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.util.Locale

fun getFormattedCurrency(currency:Int): String? =
    try {
        "${NumberFormat.getInstance(Locale(" ru ", " RU ")).format(currency)} \u20BD"
    }catch (e:Exception){
        null
    }

@RequiresApi(Build.VERSION_CODES.P)
fun convertListToStringWithBullets(list: List<String>):String? =
    if (list.isEmpty()){
        null
    }else{
        var mainString = ""

        for (item in list){
            mainString += if (list.indexOf(item) == 0)
                "\u2022 $item"
            else
                "\n\u2022 $item"
        }
        //val spannableString = SpannableString(mainString)
        //spannableString.setSpan(BulletSpan(40, Color.GREEN,40),30,52,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mainString
    }