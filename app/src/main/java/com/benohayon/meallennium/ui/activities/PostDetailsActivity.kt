package com.benohayon.meallennium.ui.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.models.POST_AUTHOR_KEY
import com.benohayon.meallennium.framework.models.POST_CONTENT_KEY
import com.benohayon.meallennium.framework.models.POST_SUMMERY_KEY
import com.benohayon.meallennium.framework.models.POST_TITLE_KEY

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var summeryTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var createdByText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        initUI()
    }

    private fun initUI() {
        initActionBar()
        summeryTextView = findViewById(R.id.postDetailsScreenSummery)
        contentTextView = findViewById(R.id.postDetailsScreenContent)
        createdByText = findViewById(R.id.postDetailsCreatedBy)

        createdByText.text = intent.getStringExtra(POST_AUTHOR_KEY)
        summeryTextView.text = intent.getStringExtra(POST_SUMMERY_KEY)
        contentTextView.text = intent.getStringExtra(POST_CONTENT_KEY)
    }

    private fun initActionBar() {
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_icon_white)
        title = intent.getStringExtra(POST_TITLE_KEY)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
