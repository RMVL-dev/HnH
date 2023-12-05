package com.example.hnhapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getFormattedCurrency(currency:String): String? =
    try {
        NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(currency.toInt())
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
        mainString
    }
fun formatDate(calendar: Calendar, pattern:String):String =
    SimpleDateFormat(pattern).format(calendar.time)

fun formatDate(date: String, newPattern: String, oldPattern: String): String {
    val oldFormatDate = SimpleDateFormat(oldPattern).parse(date) as Date
    return SimpleDateFormat(newPattern).format(oldFormatDate)
}
