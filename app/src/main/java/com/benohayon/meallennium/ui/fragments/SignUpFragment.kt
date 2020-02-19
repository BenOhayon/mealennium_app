package com.benohayon.meallennium.ui.fragments


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.managers.UserManager
import com.benohayon.meallennium.framework.utils.PopupManager
import com.benohayon.meallennium.framework.utils.MealenniumFragmentManager
import com.benohayon.meallennium.ui.activities.HomeActivity
import com.benohayon.meallennium.ui.custom_views.styled_views.StyledEditText
import com.benohayon.meallennium.ui.custom_views.styled_views.StyledTextView

class SignUpFragment : Fragment() {

    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: StyledEditText
    private lateinit var confirmPasswordEt: EditText
    private lateinit var backButton: StyledTextView
    private lateinit var signUpButton: StyledTextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        nameEt = view.findViewById(R.id.signUpScreenNameEditText)
        emailEt = view.findViewById(R.id.signUpScreenEmailEditText)
        passwordEt = view.findViewById(R.id.signUpScreenPasswordEditText)
        confirmPasswordEt = view.findViewById(R.id.signUpScreenConfirmPasswordEditText)
        progressBar = view.findViewById(R.id.signUpScreenProgressBar)
        backButton = view.findViewById(R.id.signUpScreenBackButton)
        signUpButton = view.findViewById(R.id.signUpScreenSignUpButton)

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

        backButton.setOnClickListener {
            MealenniumFragmentManager.popFragment(activity!!)
        }

        signUpButton.setOnClickListener {
            if (areFieldsValidated()) {
                progressBar.visibility = View.VISIBLE
                createUserWithEmailAndPassword(nameEt.text.toString(), emailEt.text.toString(), passwordEt.text.toString())
            }
        }

        return view
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        val emailValidPattern = Regex("^[a-zA-Z0-9]+(\\.[a-zA-Z0-9])*@[a-zA-Z]+(\\.com|\\.co\\.il)$")
        return email.matches(emailValidPattern)
    }

    private fun areFieldsValidated(): Boolean {
        var flag = true
        val invalidatedBackground = resources.getDrawable(R.drawable.invalidated_field_background, null)

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
            PopupManager.showInformationPopup(activity!!, getString(R.string.alert_field_validation_error_title), getString(R.string.alert_field_validation_error_message))
        }

        return flag
    }

    private fun createUserWithEmailAndPassword(name: String, email: String, password: String) {
        FirebaseManager.createUserWithEmailAndPassword(name, email, password,
                onSuccess = {
                    progressBar.visibility = View.INVISIBLE
                    UserManager.storeLoginMethod(activity!!, FirebaseManager.LoginMethod.EmailPassword)
                    UserManager.storeName(activity!!, name)
                    UserManager.storeEmail(activity!!, email)
                    progressBar.visibility = View.INVISIBLE
                    val toPostListActivity = Intent(activity!!, HomeActivity::class.java)
                    startActivity(toPostListActivity)
                }, onFail = { errorMessage ->
            progressBar.visibility = View.INVISIBLE
            PopupManager.showInformationPopup(activity!!, getString(R.string.alert_user_authentication_failed_title), errorMessage)
        })
    }
}
