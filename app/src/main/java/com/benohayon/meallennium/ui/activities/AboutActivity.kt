package com.benohayon.meallennium.ui.activities

import android.os.Bundle
import com.benohayon.meallennium.BuildConfig
import com.benohayon.meallennium.R
import com.benohayon.meallennium.ui.activities.abs.BaseActivity
import com.benohayon.meallennium.ui.custom_views.TopActionBar
import com.benohayon.meallennium.ui.custom_views.styled_views.StyledTextView

class AboutActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_about

    private lateinit var versionText: StyledTextView
    private lateinit var topActionBar: TopActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
    }

    private fun initUI() {
        versionText = findViewById(R.id.aboutActivityVersionValueText)
        topActionBar = findViewById(R.id.aboutActivityTopActionBar)
        topActionBar.setLeftButtonResource(R.drawable.back_icon_white)
        topActionBar.setLeftButtonOnClickListener { finish() }
        topActionBar.centerText = getString(R.string.about_screen_top_action_bar_center_text)
        versionText.text = BuildConfig.VERSION_NAME
    }
}
