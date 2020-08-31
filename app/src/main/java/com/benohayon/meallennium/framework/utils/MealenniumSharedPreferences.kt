package com.benohayon.meallennium.framework.utils

import android.content.Context
import android.content.SharedPreferences
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.models.USER_EMAIL_KEY
import com.benohayon.meallennium.framework.models.USER_FIRST_NAME_KEY
import com.benohayon.meallennium.framework.models.USER_LAST_NAME_KEY
import com.benohayon.meallennium.framework.models.USER_LOGIN_METHOD_KEY

class MealenniumSharedPreferences(val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("meallennium", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String?) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun storeLoginMethod(key: String, loginMethod: FirebaseManager.LoginMethod?) {
        sharedPreferences.edit()
                .putInt(key, getLoginMethodInteger(loginMethod))
                .apply()
    }

    // EmailPassword => 0, Facebook -> 1, Google -> 2
    fun getLoginMethod(key: String): FirebaseManager.LoginMethod? {
        return getLoginMethodFromInteger(sharedPreferences.getInt(key, -1))
    }

    fun clearValues() {
        storeString(USER_FIRST_NAME_KEY, null)
        storeString(USER_LAST_NAME_KEY, null)
        storeString(USER_EMAIL_KEY, null)
        storeString(USER_LOGIN_METHOD_KEY, null)
    }

    private fun getLoginMethodInteger(loginMethod: FirebaseManager.LoginMethod?): Int =
            when (loginMethod) {
                FirebaseManager.LoginMethod.EmailPassword -> 0
                FirebaseManager.LoginMethod.Facebook -> 1
                FirebaseManager.LoginMethod.Google -> 2
                else -> -1
            }

    private fun getLoginMethodFromInteger(loginMethodInt: Int): FirebaseManager.LoginMethod? =
            when (loginMethodInt) {
                0 -> FirebaseManager.LoginMethod.EmailPassword
                1 -> FirebaseManager.LoginMethod.Facebook
                2 -> FirebaseManager.LoginMethod.Google
                else -> null
            }
}