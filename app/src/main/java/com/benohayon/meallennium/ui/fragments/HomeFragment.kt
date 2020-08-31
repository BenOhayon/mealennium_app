package com.benohayon.meallennium.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.managers.UserManager
import com.benohayon.meallennium.framework.utils.PopupManager
import com.benohayon.meallennium.framework.utils.MealenniumFragmentManager
import com.benohayon.meallennium.ui.activities.HomeActivity
import com.benohayon.meallennium.ui.custom_views.DontHaveAccountFrame
import com.benohayon.meallennium.ui.custom_views.FacebookSignInButton
import com.benohayon.meallennium.ui.custom_views.GoogleSignInButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private val GOOGLE_SIGN_IN_REQUEST_CODE = 4567

    private lateinit var dontHaveAccountFrame: DontHaveAccountFrame
    private lateinit var signInWithEmailButton: TextView
    private lateinit var facebookSignInButton: FacebookSignInButton
    private lateinit var googleSignInButton: GoogleSignInButton
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar = view.findViewById(R.id.homeScreenProgressBar)
        dontHaveAccountFrame = view.findViewById(R.id.homeScreenDontHaveAccountFrame)
        dontHaveAccountFrame.setOnClickListener {
            MealenniumFragmentManager.moveToFragment(activity!!, SignUpFragment(), R.id.homeFragmentContainer)
        }

        facebookSignInButton = view.findViewById(R.id.homeScreenSignInWithFacebookButton)
        facebookSignInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            FirebaseManager.connectWithFacebook(this, onSuccess = {
                progressBar.visibility = View.INVISIBLE
                UserManager.storeLoginMethod(activity!!, FirebaseManager.LoginMethod.Facebook)
                val toPostListActivity = Intent(activity!!, HomeActivity::class.java)
                startActivity(toPostListActivity)
                activity!!.finish()
            }, onFail = {
                progressBar.visibility = View.INVISIBLE
                PopupManager.showInformationPopup(activity!!, getString(R.string.alert_user_authentication_failed_title), it)
            }, onCancel = {
                progressBar.visibility = View.INVISIBLE
                PopupManager.showInformationPopup(activity!!, getString(R.string.alert_user_authentication_canceled_title), getString(R.string.alert_user_authentication_canceled_message))
            })
        }

        googleSignInButton = view.findViewById(R.id.homeScreenSignInWithGoogleButton)
        googleSignInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val googleScope = arrayOf("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email")
            val firstScope = Scope(googleScope[0])
            val secondScope = Scope(googleScope[1])
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context?.getString(R.string.default_web_client_id))
                    .requestScopes(firstScope, secondScope)
                    .requestId()
                    .requestEmail()
                    .build()

            val googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
        }

        signInWithEmailButton = view.findViewById(R.id.homeScreenSignInWithEmailButton)
        signInWithEmailButton.setOnClickListener {
            MealenniumFragmentManager.moveToFragment(activity!!, SignInFragment(), R.id.homeFragmentContainer)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            FirebaseManager.connectWithGoogle(activity!!, data, onSuccess = {account ->
                // Sign in success, update UI with the signed-in user's information
                progressBar.visibility = View.INVISIBLE
                UserManager.storeLoginMethod(activity!!, FirebaseManager.LoginMethod.Google)
                UserManager.storeName(activity!!, account?.displayName)
                UserManager.storeEmail(activity!!, account?.email)
                startActivity(Intent(activity!!, HomeActivity::class.java))
                activity!!.finish()
            }, onFail = {errorMessage ->
                progressBar.visibility = View.INVISIBLE
                PopupManager.showInformationPopup(activity!!, getString(R.string.alert_user_authentication_failed_title), errorMessage)
            })
        } else
            FirebaseManager.setCallbackManagerOnActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }
}
