package com.benohayon.meallennium.ui.activities

import android.os.Bundle
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.utils.PopupManager
import com.benohayon.meallennium.framework.utils.MealenniumFragmentManager
import com.benohayon.meallennium.ui.activities.abs.BaseActivity
import com.benohayon.meallennium.ui.fragments.HomeFragment

class LoginActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MealenniumFragmentManager.moveToFragment(this, HomeFragment(), R.id.homeFragmentContainer)
    }

    override fun onBackPressed() {
        requestExit()
    }

    private fun requestExit() {
        PopupManager.showConfirmationPopup(this, getString(R.string.alert_exit_title), getString(R.string.alert_exit_message),
                onYesClick = { dialog, which ->
                    finish()
                })
    }
}
