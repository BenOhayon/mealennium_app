package com.benohayon.meallennium.framework.managers

import android.content.Context
import android.graphics.Typeface

object FontManager {

    fun getProximaNovaBoldFontFilePath(): String = "fonts/proximanova_bold.otf"
    fun getProximaNovaRegularFontFilePath(): String = "fonts/proximanova_regular.otf"
    fun getProximaNovaThinFontFilePath(): String = "fonts/proximanova_thin.otf"

    fun getProximaNovaBoldFont(context: Context): Typeface = Typeface.createFromAsset(context.assets, getProximaNovaBoldFontFilePath())
    fun getProximaNovaRegularFont(context: Context): Typeface = Typeface.createFromAsset(context.assets, getProximaNovaRegularFontFilePath())
    fun getProximaNovaThinFont(context: Context): Typeface = Typeface.createFromAsset(context.assets, getProximaNovaThinFontFilePath())

}