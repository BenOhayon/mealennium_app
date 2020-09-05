package com.benohayon.meallennium.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.managers.UserManager
import com.benohayon.meallennium.framework.models.GOOGLE_SIGN_IN_REQUEST
import com.benohayon.meallennium.framework.utils.MealenniumPopupManager
import com.benohayon.meallennium.ui.activities.abs.BaseActivity
import com.benohayon.meallennium.ui.custom_views.DontHaveAccountFrame
import com.benohayon.meallennium.ui.custom_views.FacebookSignInButton
import com.benohayon.meallennium.ui.custom_views.GoogleSignInButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope

class LoginActivity : BaseActivity() {

    private lateinit var dontHaveAccountFrame: DontHaveAccountFrame
    private lateinit var signInWithEmailButton: TextView
    private lateinit var facebookSignInButton: FacebookSignInButton
    private lateinit var googleSignInButton: GoogleSignInButton
    private lateinit var progressBar: ProgressBar

    override val layoutResource: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        progressBar = findViewById(R.id.homeScreenProgressBar)
        dontHaveAccountFrame = findViewById(R.id.homeScreenDontHaveAccountFrame)
        dontHaveAccountFrame.setOnClickListener {
            startActivityAndFinish(Intent(this, SignUpActivity::class.java))
        }

        facebookSignInButton = findViewById(R.id.homeScreenSignInWithFacebookButton)
        facebookSignInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            FirebaseManager.connectWithFacebook(this, onSuccess = {
                progressBar.visibility = View.INVISIBLE
                UserManager.storeLoginMethod(this, FirebaseManager.LoginMethod.Facebook)
                val toPostListActivity = Intent(this, HomeActivity::class.java)
                startActivityAndFinish(toPostListActivity)
            }, onFail = {
                progressBar.visibility = View.INVISIBLE
                MealenniumPopupManager.showInformationPopup(this, getString(R.string.mealennium_alert_user_authentication_failed_title), it)
            }, onCancel = {
                progressBar.visibility = View.INVISIBLE
                MealenniumPopupManager.showInformationPopup(this, getString(R.string.mealennium_alert_user_authentication_canceled_title), getString(R.string.mealennium_alert_user_authentication_canceled_message))
            })
        }

        googleSignInButton = findViewById(R.id.homeScreenSignInWithGoogleButton)
        googleSignInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val googleScope = arrayOf("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email")
            val firstScope = Scope(googleScope[0])
            val secondScope = Scope(googleScope[1])
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("10642535149-7burt9lfimsjncrguvnekiv77ffkj0ld.apps.googleusercontent.com")
                    .requestScopes(firstScope, secondScope)
                    .requestId()
                    .requestEmail()
                    .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST)
        }

        signInWithEmailButton = findViewById(R.id.homeScreenSignInWithEmailButton)
        signInWithEmailButton.setOnClickListener {
            startActivityAndFinish(Intent(this, LoginEmailActivity::class.java))
        }
    }

    override fun onBackPressed() {
        requestExit()
    }

    private fun requestExit() {
        MealenniumPopupManager.showConfirmationPopup(this, getString(R.string.mealennium_alert_exit_title), getString(R.string.mealennium_alert_exit_message),
                onYesClick = { dialog, which ->
                    finish()
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FirebaseManager.setCallbackManagerOnActivityResult(requestCode, resultCode, data)
    }
}
