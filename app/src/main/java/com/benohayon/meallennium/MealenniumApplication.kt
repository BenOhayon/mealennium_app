package com.benohayon.meallennium

import android.app.Application
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class MealenniumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val universalImageLoaderDisplayOptions: DisplayImageOptions = DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build()
        val universalImageLoaderConfiguration: ImageLoaderConfiguration = ImageLoaderConfiguration.Builder(applicationContext)
                .defaultDisplayImageOptions(universalImageLoaderDisplayOptions)
                .build()

        ImageLoader.getInstance().init(universalImageLoaderConfiguration)
    }
}