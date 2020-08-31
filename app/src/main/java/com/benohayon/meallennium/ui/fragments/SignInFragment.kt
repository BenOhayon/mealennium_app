package com.benohayon.meallennium.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.managers.UserManager
import com.benohayon.meallennium.framework.utils.PopupManager
import com.benohayon.meallennium.framework.utils.MealenniumFragmentManager
import com.benohayon.meallennium.ui.activities.HomeActivity

class SignInFragment : Fragment() {

    private lateinit var signInButton: TextView
    private lateinit var backButton: TextView
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        progressBar = view.findViewById(R.id.signInScreenProgressBar)
        emailEt = view.findViewById(R.id.signInScreenEmailEditText)
        passwordEt = view.findViewById(R.id.signInScreenPasswordEditText)
        signInButton = view.findViewById(R.id.signInScreenSignInButton)
        backButton = view.findViewById(R.id.signInScreenBackButton)

        emailEt.setOnFocusChangeListener { v, hasFocus ->
            v.background = resources.getDrawable(R.color.white, null)
        }

        passwordEt.setOnFocusChangeListener { v, hasFocus ->
            v.background = resources.getDrawable(R.color.white, null)
        }

        signInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            signInWithEmailAndPassword(emailEt.text.toString(), passwordEt.text.toString())
        }

        backButton.setOnClickListener {
            MealenniumFragmentManager.popFragment(activity!!)
        }

        return view
    }

    private fun fieldsValidated(): Boolean {
        var flag = true
        val invalidatedBackground = resources.getDrawable(R.drawable.invalidated_field_background, null)

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

    private fun signInWithEmailAndPassword(email: String, password: String) {
        if (fieldsValidated()) {
            FirebaseManager.signInWithEmailAndPassword(email, password, {
                progressBar.visibility = View.INVISIBLE
                UserManager.storeLoginMethod(activity!!, FirebaseManager.LoginMethod.EmailPassword)
                UserManager.storeEmail(activity!!, email)
                UserManager.storeName(activity!!, FirebaseManager.getCurrentUser().displayName)
                val toPostListActivity = Intent(activity!!, HomeActivity::class.java)
                startActivity(toPostListActivity)
            }, { errorMessage ->
                progressBar.visibility = View.INVISIBLE
                PopupManager.showInformationPopup(activity!!, getString(R.string.alert_user_authentication_failed_title), errorMessage)
            })
        }
    }

}
