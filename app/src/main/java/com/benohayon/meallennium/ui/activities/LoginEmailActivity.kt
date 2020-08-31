package com.benohayon.meallennium.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.managers.UserManager
import com.benohayon.meallennium.framework.utils.MealenniumPopupManager
import com.benohayon.meallennium.ui.activities.abs.BaseActivity

class LoginEmailActivity : BaseActivity() {

    private lateinit var signInButton: TextView
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: ImageView

    override val layoutResource: Int
        get() = R.layout.activity_login_email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        progressBar = findViewById(R.id.loginEmailActivityProgressBar)
        emailEt = findViewById(R.id.loginEmailActivityEmailEditText)
        passwordEt = findViewById(R.id.loginEmailActivityPasswordEditText)
        signInButton = findViewById(R.id.loginEmailActivitySignInButton)
        backButton = findViewById(R.id.loginEmailActivityBackButton)

        emailEt.setOnFocusChangeListener { v, hasFocus ->
            v.background = ResourcesCompat.getDrawable(resources, R.color.white, null)
        }

        passwordEt.setOnFocusChangeListener { v, hasFocus ->
            v.background = ResourcesCompat.getDrawable(resources, R.color.white, null)
        }

        signInButton.setOnClickListener {
            if (fieldsValidated()) {
                progressBar.visibility = View.VISIBLE
                signInWithEmailAndPassword(emailEt.text.toString(), passwordEt.text.toString())
            }
        }

        backButton.setOnClickListener {
            startActivityAndFinish(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        FirebaseManager.signInWithEmailAndPassword(email, password, {
            progressBar.visibility = View.INVISIBLE
            UserManager.storeLoginMethod(this, FirebaseManager.LoginMethod.EmailPassword)
            UserManager.storeEmail(this, email)
            UserManager.storeName(this, FirebaseManager.getCurrentUser().displayName)
            openHomeActivity()
        }, { errorMessage ->
            progressBar.visibility = View.INVISIBLE
            MealenniumPopupManager.showInformationPopup(this, resources.getString(R.string.alert_user_authentication_failed_title), errorMessage)
        })
    }

    private fun openHomeActivity() {
        val toPostListActivity = Intent(this, HomeActivity::class.java)
        startActivity(toPostListActivity)
    }

    private fun fieldsValidated(): Boolean {
        var flag = true
        val invalidatedBackground = ResourcesCompat.getDrawable(resources, R.drawable.invalidated_field_background, null)

        if (emailEt.text.isEmpty()) {
            flag = false
            emailEt.background = invalidatedBackground
        }

        if (passwordEt.text.isEmpty()) {
            flag = false
            passwordEt.background = invalidatedBackground
        }

        return flag
    }
}