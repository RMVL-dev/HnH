package com.example.hnhapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.example.hnhapp.R
import com.example.hnhapp.databinding.ErrorScreenBinding

class ErrorScreen @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private var binding: ErrorScreenBinding? = null

    init {
        binding = ErrorScreenBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.error_screen, this, true)
        )
    }


    fun setErrorState(click:()->Unit, errorMes: String) = binding?.run {
        errorImage.visibility  = View.VISIBLE
        errorHeader.visibility  = View.VISIBLE
        errorHeader.text = resources.getText(R.string.error_header)
        errorMessage.visibility  = View.VISIBLE
        errorMessage.text = errorMes
        errorUpdateButton.visibility  = View.VISIBLE
        errorUpdateButton.setOnClickListener {
            click()
        }
    }

    fun setErrorState(click:()->Unit) = binding?.run {
        errorImage.visibility  = View.VISIBLE
        errorHeader.visibility  = View.VISIBLE
        errorHeader.text = resources.getText(R.string.empty_list_header)
        errorMessage.visibility  = View.VISIBLE
        errorMessage.text = resources.getText(R.string.empty_list_message)
        errorUpdateButton.visibility  = View.VISIBLE
        errorUpdateButton.setOnClickListener {
            click()
        }
    }

    fun setOkState() = binding?.run {
        errorImage.visibility  = View.GONE
        errorHeader.visibility  = View.GONE
        errorMessage.visibility  = View.GONE
        errorUpdateButton.visibility  = View.GONE
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

}