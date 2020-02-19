package com.benohayon.meallennium.framework.interfaces

import android.view.animation.Animation

abstract class OnAnimationFinishListener : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {}

    abstract override fun onAnimationEnd(animation: Animation?)

    override fun onAnimationStart(animation: Animation?) {}
}