package com.benohayon.meallennium.framework.managers

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


object GoogleManager {

    private val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder().requestEmail().build()

    fun signOut(activity: FragmentActivity, onComplete: () -> Unit) {
        // Google sign out
        val mGoogleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
        mGoogleSignInClient.signOut().addOnCompleteListener(activity) { onComplete() }
    }

    fun checkLoginStatus(context: Context, userLoggedInCallback: () -> Unit, userLoggedOutCallback: () -> Unit) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) { // user logged in
            UserManager.storeName(context, account.displayName)
            UserManager.storeEmail(context, account.email)
            userLoggedInCallback()
        } else
            userLoggedOutCallback()
    }

}