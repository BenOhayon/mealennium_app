package com.benohayon.meallennium.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.benohayon.meallennium.R

class TopActionBar : LinearLayout {

    var centerText: String
        get() = centerTextView.text.toString()
        set(value) {
            centerTextView.text = value
        }

    private lateinit var leftButton: ImageView
    private lateinit var rightButton: ImageView
    private lateinit var centerTextView: TextView
    private lateinit var searchBoxEditText: EditText

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
        inflater.inflate(R.layout.view_top_action_bar, this)
        initUI()
    }

    private fun initUI() {
        leftButton = findViewById(R.id.topActionBarLeftButton)
        rightButton = findViewById(R.id.topActionBarRightButton)
        centerTextView = findViewById(R.id.topActionBarCenterText)
        searchBoxEditText = findViewById(R.id.topActionBarSearchBoxEditText)
    }

    fun setLeftButtonResource(resource: Int) {
        leftButton.setImageResource(resource)
    }

    fun setRightButtonResource(resource: Int) {
        rightButton.setImageResource(resource)
    }

    fun setLeftButtonOnClickListener(onClick: (View) -> Unit) {
        leftButton.setOnClickListener(onClick)
    }

    fun setRightButtonOnClickListener(onClick: (View) -> Unit) {
        rightButton.setOnClickListener(onClick)
    }

    fun disableRightButton() {
        rightButton.isClickable = false
    }

    fun enableRightButton() {
        rightButton.isClickable = true
    }

    fun disableLeftButton() {
        leftButton.isClickable = false
    }

    fun enableLeftButton() {
        leftButton.isClickable = true
    }

    fun setSearchBoxSearchListener(listener: TextView.OnEditorActionListener) {
        searchBoxEditText.setOnEditorActionListener(listener)
    }
}