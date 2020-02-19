package com.benohayon.meallennium.ui.custom_views.styled_views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.benohayon.meallennium.R
import java.util.*

object CustomFontGenerator {

    private val typeFaces = Hashtable<Int, Typeface>()

    fun setCustomFont(context: Context, attrs: AttributeSet?, textView: TextView) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.stylableView)
        val customFont = typedArray.getInteger(R.styleable.stylableView_fontType, 0)
        setCustomFont(context, customFont, textView)
        typedArray.recycle()
    }

    private fun setCustomFont(context: Context, customFont: Int, textView: TextView) {
        val customTypeface: Typeface?
        when (customFont) {
            0 -> {
                if (typeFaces.contains(customFont))
                    customTypeface = typeFaces[customFont]
                else {
                    customTypeface = Typeface.createFromAsset(context.assets, "fonts/ProximaNova-Regular.otf")
                    typeFaces[customFont] = customTypeface
                }
            }

            1 -> {
                if (typeFaces.contains(customFont))
                    customTypeface = typeFaces[customFont]
                else {
                    customTypeface = Typeface.createFromAsset(context.assets, "fonts/ProximaNova-Bold.otf")
                    typeFaces[customFont] = customTypeface
                }
            }

            2 -> {
                if (typeFaces.contains(customFont))
                    customTypeface = typeFaces[customFont]
                else {
                    customTypeface = Typeface.createFromAsset(context.assets, "fonts/ProximaNova-Thin.otf")
                    typeFaces[customFont] = customTypeface
                }
            }

            else -> {
                if (typeFaces.contains(customFont))
                    customTypeface = typeFaces[customFont]
                else {
                    customTypeface = Typeface.createFromAsset(context.assets, "fonts/ProximaNova-Regular.otf")
                    typeFaces[customFont] = customTypeface
                }
            }
        }

        textView.typeface = customTypeface
    }

    fun setDefaultFont(textView: TextView) {
        val tf = Typeface.createFromAsset(textView.context.assets, "fonts/ProximaNova-Regular.otf")
        textView.typeface = tf
    }

}
