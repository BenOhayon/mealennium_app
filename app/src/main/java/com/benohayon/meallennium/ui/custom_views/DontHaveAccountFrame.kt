package com.benohayon.meallennium.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.benohayon.meallennium.R

class DontHaveAccountFrame : LinearLayout {

    private var signInText: TextView? = null

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.view_dont_have_account_frame, this)

        initUI(view)
    }

    private fun initUI(view: View) {
        signInText = view.findViewById(R.id.signInText)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        signInText?.setOnClickListener(l)
    }
}