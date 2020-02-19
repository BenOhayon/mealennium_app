package com.benohayon.meallennium.ui.custom_views.styled_views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class StyledEditText : EditText {

    constructor(context: Context) : super(context) {
        CustomFontGenerator.setDefaultFont(this)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        CustomFontGenerator.setCustomFont(context, attrs, this)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        CustomFontGenerator.setCustomFont(context, attrs, this)
    }
}
