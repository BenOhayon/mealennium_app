package com.benohayon.meallennium.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.benohayon.meallennium.R

class AppTitleView : LinearLayout {

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defaultStyle: Int) : super(context, attrs, defaultStyle) {
        init(context)
    }

    private fun init(context: Context?) {
        val inflater: LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_app_title, this)
    }

}