package com.example.hnhapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.hnhapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator

class ButtonWithIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val firstChild: View? get() = if ( childCount>0 ) getChildAt(0) else null
    private val secondChild: View? get() = if ( childCount>1 ) getChildAt(1) else null

    private var width = 0
    private var height = 0

    private var firstChildHeight = 0
    private var firstChildWidth = 0
    private var secondChildHeight = 0
    private var secondChildWidth = 0


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        checkChildCount()


        firstChild?.measure(widthMeasureSpec,heightMeasureSpec)
        secondChild?.measure(widthMeasureSpec,heightMeasureSpec)

        firstChildHeight = firstChild?.measuredHeight ?: 0
        firstChildWidth = firstChild?.measuredWidth ?: 0
        secondChildHeight = secondChild?.measuredHeight ?: 0
        secondChildWidth = secondChild?.measuredWidth ?: 0


        width = MeasureSpec.getSize(widthMeasureSpec)
        height = firstChildHeight
        setMeasuredDimension(width,height)
    }
    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        firstChild?.layout(
            0,0,width,height
        )
        secondChild?.layout(
            (width- secondChildWidth)/2,
            (height - secondChildHeight)/2,
            width - ((width- secondChildWidth)/2),
            height - ((height - secondChildHeight)/2)
        )

    }

    fun loading(){
        (firstChild as MaterialButton).text = ""
        (secondChild as CircularProgressIndicator).show()
    }

    fun otherStates(buttonTextId:Int){
        (firstChild as MaterialButton).text = context.getText(buttonTextId)
        (secondChild as CircularProgressIndicator).hide()
    }
    fun otherStates(buttonText:String){
        (firstChild as MaterialButton).text = buttonText
        (secondChild as CircularProgressIndicator).hide()
    }

    fun login(signIn:()->Unit){
        (firstChild as MaterialButton).setOnClickListener {
            signIn()
        }
    }

    fun buttonState(enable:Boolean){
        (firstChild as MaterialButton).isEnabled = enable
    }

    private fun checkChildCount(){
        if (childCount>2) error("This custom view group should not contain more than 2 view's")
    }
}