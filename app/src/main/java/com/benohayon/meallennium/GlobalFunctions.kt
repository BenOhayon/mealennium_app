package com.benohayon.meallennium

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.benohayon.meallennium.framework.managers.FontManager

fun createCustomViewForActionBar(context: Context, text: CharSequence): View {
    val customActionBarText = TextView(context)
    customActionBarText.text = text
    customActionBarText.typeface = FontManager.getProximaNovaRegularFont(context)
    customActionBarText.ellipsize = TextUtils.TruncateAt.END
    return customActionBarText
}