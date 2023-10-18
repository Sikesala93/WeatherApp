package com.example.edistynytmobiiliohjelmointi2022

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

class LatestDataView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    val maxRows = 5
    init
    {
        this.orientation = VERTICAL
        // tehdään muistiin yksi textview mittaamista varten
        var someTextView : TextView =  TextView(context)
        // annetaan Androidin mitata textviewin koko
        someTextView.measure(0, 0)
        // viisi textviewiä, on mitattu korkeus kertaa 5 (maxRows)
        var height = someTextView.measuredHeight * maxRows
        // mitataan myös itse LinearLayoutin oma peruskorkeus (esim. paddingit)
        this.measure(0, 0)
        var additionalHeight = this.measuredHeight
        // asetetaan koko
        this.minimumHeight = height + additionalHeight
    }
    fun addData(message : String)
    {
        var newTextView : TextView = TextView(context)
        newTextView.setBackgroundColor(Color.BLACK)
        newTextView.setTextColor(Color.YELLOW)
        newTextView.text = message
        while(this.childCount >= maxRows)
        {
            this.removeViewAt(0)
        }
        this.addView(newTextView)
    }
}