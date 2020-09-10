package com.benohayon.meallennium.ui.activities.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.benohayon.meallennium.R
import com.benohayon.meallennium.ui.activities.AboutActivity
import com.benohayon.meallennium.ui.activities.abs.BaseActivity

class MainSettingsActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_main_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initActionBar()
        findViewById<View>(R.id.settingsActivityAccountOption).setOnClickListener { openAccountSettings() }
        findViewById<View>(R.id.settingsActivityThemeOption).setOnClickListener { openThemeSettings() }
        findViewById<View>(R.id.settingsActivityAboutOption).setOnClickListener { openAboutScreen() }
    }

    private fun initActionBar() {
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun openAccountSettings() {
        startActivity(Intent(this, AccountSettingsActivity::class.java))
    }

    private fun openThemeSettings() {
        startActivity(Intent(this, ThemeSettingsActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (supportFragmentManager.popBackStackImmediate()) {
            true
        } else {
            finish()
            true
        }
    }
}