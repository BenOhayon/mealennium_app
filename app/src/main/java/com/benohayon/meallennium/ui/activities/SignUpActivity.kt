package com.benohayon.meallennium.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

class SignUpActivity : BaseActivity() {

    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confirmPasswordEt: EditText
    private lateinit var backButton: ImageView
    private lateinit var signUpButton: TextView
    private lateinit var progressBar: ProgressBar

    override val layoutResource: Int
        get() = R.layout.activity_sign_up

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        nameEt = findViewById(R.id.signUpActivityNameEditText)
        emailEt = findViewById(R.id.signUpActivityEmailEditText)
        passwordEt = findViewById(R.id.signUpActivityPasswordEditText)
        confirmPasswordEt = findViewById(R.id.signUpActivityConfirmPasswordEditText)
        progressBar = findViewById(R.id.signUpActivityProgressBar)
        backButton = findViewById(R.id.signUpActivityBackButton)
        signUpButton = findViewById(R.id.signUpActivitySignUpButton)

        emailEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidEmail(s!!))
                    emailEt.setTextColor(Color.BLACK)
                else
                    emailEt.setTextColor(Color.RED)
            }

        })

        signUpButton.setOnClickListener {
            if (textFieldsValidated()) {
                progressBar.visibility = View.VISIBLE
                createUserWithEmailAndPassword(nameEt.text.toString(), emailEt.text.toString(), passwordEt.text.toString())
            }
        }

        backButton.setOnClickListener {
            startActivityAndFinish(Intent(this, LoginActivity::class.java))
        }
    }

    private fun createUserWithEmailAndPassword(name: String, email: String, password: String) {
        FirebaseManager.createUserWithEmailAndPassword(name, email, password,
                onSuccess = {
                    progressBar.visibility = View.INVISIBLE
                    UserManager.storeLoginMethod(this, FirebaseManager.LoginMethod.EmailPassword)
                    UserManager.storeName(this, name)
                    UserManager.storeEmail(this, email)
                    progressBar.visibility = View.INVISIBLE
                    openHomeActivity()
                }, onFail = { errorMessage ->
                    progressBar.visibility = View.INVISIBLE
                    MealenniumPopupManager.showInformationPopup(this, resources.getString(R.string.mealennium_alert_user_authentication_failed_title), errorMessage)
        })
    }

    private fun openHomeActivity() {
        val toPostListActivity = Intent(this, HomeActivity::class.java)
        startActivityAndFinish(toPostListActivity)
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        val emailValidPattern = Regex("^[a-zA-Z0-9]+(\\.[a-zA-Z0-9])*@[a-zA-Z]+(\\.com|\\.co\\.il)$")
        return email.matches(emailValidPattern)
    }

    private fun textFieldsValidated(): Boolean {
        var flag = true
        val invalidatedBackground = ResourcesCompat.getDrawable(resources, R.drawable.invalidated_field_background, null)

        if (nameEt.text?.length == 0) {
            flag = false
            nameEt.background = invalidatedBackground
        }

        if (emailEt.text?.length == 0) {
            flag = false
            emailEt.background = invalidatedBackground
        }

        if (passwordEt.text!!.isEmpty()) {
            flag = false
            passwordEt.background = invalidatedBackground
        }

        if (confirmPasswordEt.text.isEmpty()) {
            flag = false
            confirmPasswordEt.background = invalidatedBackground
        }

        if (passwordEt.text.toString() != confirmPasswordEt.text.toString()) {
            flag = false
            MealenniumPopupManager.showInformationPopup(this, resources.getString(R.string.mealennium_alert_field_validation_error_title), getString(R.string.mealennium_alert_field_validation_error_message))
        }

        return flag
    }
}