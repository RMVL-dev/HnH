package com.example.hnhapp.presentation.orderFragment

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

class DatePicker(
    private val context:Context,
    private val listener:DatePickerDialog.OnDateSetListener
) {

    fun createDialog():DatePickerDialog{
        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return DatePickerDialog(context,listener,year,month,date)
    }

}