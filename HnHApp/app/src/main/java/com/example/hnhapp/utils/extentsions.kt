package com.example.hnhapp.utils

import android.view.View
import com.example.hnhapp.R
import com.google.android.material.snackbar.Snackbar


fun View.settingSnackBar(): Snackbar =
    Snackbar
        .make(this, context.resources.getText(R.string.wrong), Snackbar.LENGTH_LONG)
        .setBackgroundTint(context.resources.getColor(R.color.error_sign_in))
        .setTextColor(context.resources.getColor(R.color.seashell))