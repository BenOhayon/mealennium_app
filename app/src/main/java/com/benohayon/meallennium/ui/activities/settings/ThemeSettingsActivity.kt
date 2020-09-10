package com.benohayon.meallennium.ui.activities.settings

import android.os.Bundle
import com.benohayon.meallennium.R
import com.benohayon.meallennium.ui.activities.abs.BaseActivity

class ThemeSettingsActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_theme_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}