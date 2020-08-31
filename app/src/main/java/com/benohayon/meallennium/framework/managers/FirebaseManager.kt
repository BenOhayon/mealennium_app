package com.benohayon.meallennium.framework.managers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.benohayon.meallennium.framework.models.Post
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


object FirebaseManager {

    enum class LoginMethod {
        EmailPassword,
        Facebook,
        Google
    }

    private val TAG = "FirebaseManager"
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser: FirebaseUser? = auth.currentUser
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private val facebookLoginManager: LoginManager = LoginManager.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference

    fun createUserWithEmailAndPassword(name: String, email: String, password: String, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { createUserTask ->
                    if (createUserTask.isSuccessful) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build()

                        firebaseUser?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { updateUserTask ->
                                    if (updateUserTask.isComplete)
                                        onSuccess()
                                }
                    } else onFail(createUserTask.exception.toString())
                }
    }

    private fun uploadImage(context: Context, imageUri: Uri, pictureName: String?, onSuccess: (Uri) -> Unit, onFail: (String) -> Unit) {
        val storageRef = firebaseStorage.child("images/${UserManager.getEmail(context)}/${pictureName ?: System.currentTimeMillis()}")
        storageRef.putFile(imageUri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener(onSuccess)
                }
                .addOnFailureListener {
                    onFail(it.toString())
                }
    }

    fun addPost(context: Context, newPost: Post, imageUri: Uri?, pictureName: String?, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        imageUri?.let {
            uploadImage(context, imageUri, pictureName, onSuccess = { downloadedImageUri: Uri ->
                newPost.imageLogoUrl = downloadedImageUri.toString()
                getFirebaseDatabase().collection("posts")
                        .add(newPost)
                        .addOnSuccessListener {
                            Log.d(TAG, "addPost: adding new post succeeded")
                            onSuccess()
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "addPost: adding new post failed")
                            onFail(it.message!!)
                        }
            }, onFail = {
                onFail(it)
            })
        } ?: getFirebaseDatabase().collection("posts")
                .add(newPost)
                .addOnSuccessListener {
                    Log.d(TAG, "addPost: adding new post succeeded")
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.d(TAG, "addPost: adding new post failed")
                    onFail(it.message!!)
                }
    }

    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) onSuccess()
                    else onFail(it.exception.toString())
                }
    }

    fun connectWithGoogle(activity: FragmentActivity, data: Intent?, onSuccess: (GoogleSignInAccount?) -> Unit, onFail: (String) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)

            // move to main activity
            val credential = GoogleAuthProvider.getCredential(account?.id, null)
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(activity) {
                        if (task.isSuccessful) {
                            onSuccess(account)
                        } else {
                            // If sign in fails, display a message to the user.
                            onFail(task.exception?.message!!)
                        }
                    }
        } catch (e: ApiException) {
            onFail(e.message!!)
        }
    }

    fun connectWithFacebook(fragment: Fragment, onSuccess: () -> Unit, onFail: (String) -> Unit, onCancel: () -> Unit) {
        facebookLoginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                FacebookManager.loadFacebookUserDetails(fragment.context!!, AccessToken.getCurrentAccessToken(), userLoggedInCallback = {
                    onSuccess()
                })
            }

            override fun onCancel() {
                onCancel()
            }

            override fun onError(error: FacebookException?) {
                onFail(error?.message!!)
            }
        })

        facebookLoginManager.logInWithReadPermissions(fragment, mutableListOf("email", "public_profile"))
    }

    fun setCallbackManagerOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun signOutFromFacebook(onComplete: () -> Unit) {
        FacebookManager.signOut(onComplete)
    }

    private fun signOutFromGoogle(activity: FragmentActivity, onComplete: () -> Unit) {
        auth.signOut()
        GoogleManager.signOut(activity, onComplete)
    }

    fun checkLoginStatus(context: Context, userLoggedInCallback: () -> Unit, userLoggedOutCallback: () -> Unit) {
        val loginMethod = UserManager.getLoginMethod(context)
        if (loginMethod != null) {
            when (loginMethod) {
                LoginMethod.EmailPassword -> {
                    if (firebaseUser != null) {
                        UserManager.storeName(context, firebaseUser.displayName)
                        UserManager.storeEmail(context, firebaseUser.email)
                        userLoggedInCallback()
                    } else
                        userLoggedOutCallback()
                }

                LoginMethod.Facebook -> {
                    FacebookManager.checkLoginStatus(context, userLoggedInCallback, userLoggedOutCallback)
                }

                LoginMethod.Google -> {
                    GoogleManager.checkLoginStatus(context, userLoggedInCallback, userLoggedOutCallback)
                }
            }
        } else
            userLoggedOutCallback()
    }

    fun signOut(context: Context, onComplete: () -> Unit) {
        when (UserManager.getLoginMethod(context)) {
            LoginMethod.Facebook -> {
                auth.signOut()
                signOutFromFacebook {
                    UserManager.clearValues(context)
                    onComplete()
                }
            }

            LoginMethod.Google -> {
                signOutFromGoogle(context as FragmentActivity) {
                    UserManager.clearValues(context)
                    onComplete()
                }
            }

            LoginMethod.EmailPassword -> {
                auth.signOut()
                UserManager.clearValues(context)
                onComplete()
            }
        }
    }

    fun getCurrentUser(): FirebaseUser {
        return auth.currentUser!!
    }

    private fun getFirebaseDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}