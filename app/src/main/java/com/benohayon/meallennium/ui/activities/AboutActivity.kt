package com.benohayon.meallennium.ui.activities

import android.os.Bundle
import android.widget.TextView
import com.benohayon.meallennium.BuildConfig
import com.benohayon.meallennium.R
import com.benohayon.meallennium.ui.activities.abs.BaseActivity

class AboutActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_about

    private lateinit var versionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initActionBar()
        versionText = findViewById(R.id.aboutActivityVersionValueText)
        versionText.text = BuildConfig.VERSION_NAME
    }

    private fun initActionBar() {
        supportActionBar?.show()
        title = getString(R.string.mealennium_about_screen_top_action_bar_center_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_icon_white)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
