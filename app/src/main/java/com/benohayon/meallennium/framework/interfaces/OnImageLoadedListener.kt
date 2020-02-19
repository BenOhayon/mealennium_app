package com.benohayon.meallennium.framework.interfaces

import android.graphics.Bitmap
import android.view.View
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener

open class OnImageLoadedListener : ImageLoadingListener {
    override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {}

    override fun onLoadingStarted(imageUri: String?, view: View?) {}

    override fun onLoadingCancelled(imageUri: String?, view: View?) {}

    override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {}
}