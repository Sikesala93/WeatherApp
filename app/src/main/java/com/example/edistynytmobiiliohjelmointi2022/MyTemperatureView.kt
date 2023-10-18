package com.example.edistynytmobiiliohjelmointi2022

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyTemperatureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val size = 100
    private var temperature = 0f
    // vaihdetaan aktiivinen lämpötila
    fun setTemperature(t : Float)
    {
        temperature = t
        if(temperature > 0)
        {
            //paint.color = Color.RED
            paint.color = Color.parseColor("#e31b50")
        }
        else
        {
            paint.color = Color.BLUE
        }
        // kerrotaan Androidille että data muuttui
        // => piirretään custom view uudestaan näytölle
        invalidate()
        requestLayout()
    }
    // your helper variables etc. can be here
    init
    {
        paint.color = Color.BLUE
        textPaint.color = Color.WHITE
        textPaint.textSize = 100f
        textPaint.textAlign = Paint.Align.CENTER
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // here you can do all the drawing
        // you can do all the drawing through the canvas-object
        // parameters: x-coordinate, y-coordinate, size, color
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), (width / 2).toFloat(), paint)
        // parameters: content, x, y, color
        canvas.drawText(temperature.toString() + "C", (width / 2).toFloat(), (width / 2).toFloat() + 40f, textPaint);
    }
    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int){
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // Android uses this to determine the exact size of your component on screen
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        var w: Int = View.resolveSizeAndState(minw, widthMeasureSpec, 1)
        // if no exact size given (either dp or match_parent)
        // use this one instead as default (wrap_content)
        if (w == 0)
        {
            w = size * 2
        }
        // Whatever the width ends up being, ask for a height that would let the view
        // get as big as it can
        // val minh: Int = View.MeasureSpec.getSize(w) + paddingBottom + paddingTop
        // in this case, we use the height the same as our width, since it's a circle
        val h: Int = View.resolveSizeAndState(
            View.MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        setMeasuredDimension(w, h)
    }
}