package com.example.hnhapp.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class ButtonWithProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //values for test
    private val testText = "Text".uppercase()

    //measures display
    private var height = 0
    private var width = 0

    //paint's
    private val buttonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 50f
    }

    //gesture detector
    private val gestureDetector = GestureDetector(
        context,
        object: GestureDetector.OnGestureListener{
            override fun onDown(p0: MotionEvent): Boolean = false

            override fun onShowPress(p0: MotionEvent) {}

            override fun onSingleTapUp(p0: MotionEvent): Boolean = false

            override fun onScroll(
                p0: MotionEvent?,
                p1: MotionEvent,
                p2: Float,
                p3: Float
            ): Boolean = false

            override fun onLongPress(p0: MotionEvent) {
                animated()
            }

            override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean = false
        }
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        height = 100
        width = MeasureSpec.getSize(widthMeasureSpec)
        Log.d("height", "$height")
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(0f,0f,width.toFloat(),height.toFloat(),20f,20f,buttonPaint)
        canvas.drawText(testText, findXStartPoint(testText), findYStartPoint(testText),textPaint)
    }

    fun animated(){}

    /**
     * хелпер для поиска начальной точки
     */
    private fun findXStartPoint(text:String):Float =
        width/2f - textPaint.measureText(text)/2f

    private fun findYStartPoint(text:String):Float {

        val fontMetrics = textPaint.fontMetrics
        val fontHeight = fontMetrics.descent - fontMetrics.ascent
        val offset = height - (height - fontHeight)/2 - fontHeight/2.5f
        Log.d("height", "font height = $fontHeight height - $height top point - ${offset}")
        return offset
    }
}