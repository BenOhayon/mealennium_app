package com.benohayon.meallennium.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.interfaces.OnAnimationFinishListener
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.models.SPLASH_ACTIVITY_LOGO_IMAGE_FADE_ANIMATION_DURATION
import com.benohayon.meallennium.framework.models.SPLASH_ACTIVITY_MOVE_TO_NEXT_SCREEN_DELAY
import com.benohayon.meallennium.ui.activities.abs.BaseActivity

class SplashActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_splash

    private lateinit var logoFrame: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()

        animateSplash {
            checkLoginStatus()
        }
    }

    private fun initUI() {
        logoFrame = findViewById(R.id.splashActivityLogoFrame)
    }

    private fun animateSplash(onAnimationFinish: () -> Unit) {
        Handler().postDelayed({
            fadeOutSplashScreen {
                onAnimationFinish()
            }
        }, 1000)
    }

    private fun fadeOutSplashScreen(onAnimationFinish: () -> Unit) {
        startAlphaAnimation(onAnimationFinish)
    }

    private fun startAlphaAnimation(onAnimationFinish: () -> Unit) {
        AlphaAnimation(0f, 1f).apply {
            duration = SPLASH_ACTIVITY_LOGO_IMAGE_FADE_ANIMATION_DURATION
            fillAfter = true
            setAnimationListener(object : OnAnimationFinishListener() {
                override fun onAnimationEnd(animation: Animation?) {
                    onAnimationFinish()
                }
            })
            logoFrame.startAnimation(this)
        }
    }

    private fun openHomeScreen() {
        Handler().postDelayed({
            val toSignInActivity = Intent(this, LoginActivity::class.java)
            startActivity(toSignInActivity)
            finish()
        }, SPLASH_ACTIVITY_MOVE_TO_NEXT_SCREEN_DELAY)
    }

    private fun openPostListScreen() {
        Handler().postDelayed({
            val toPostListActivity = Intent(this, HomeActivity::class.java)
            startActivity(toPostListActivity)
            finish()
        }, SPLASH_ACTIVITY_MOVE_TO_NEXT_SCREEN_DELAY)
    }

    private fun checkLoginStatus() {
        FirebaseManager.checkLoginStatus(this, userLoggedInCallback = {
            openPostListScreen()
        }, userLoggedOutCallback = {
            openHomeScreen()
        })
    }
}
