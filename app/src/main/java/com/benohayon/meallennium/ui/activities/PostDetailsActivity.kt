package com.benohayon.meallennium.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.models.POST_AUTHOR_KEY
import com.benohayon.meallennium.framework.models.POST_CONTENT_KEY
import com.benohayon.meallennium.framework.models.POST_SUMMERY_KEY
import com.benohayon.meallennium.framework.models.POST_TITLE_KEY
import com.benohayon.meallennium.ui.custom_views.TopActionBar
import com.benohayon.meallennium.ui.custom_views.styled_views.StyledTextView

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var summeryTextView: StyledTextView
    private lateinit var contentTextView: StyledTextView
    private lateinit var createdByText: StyledTextView
    private lateinit var topActionBar: TopActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        topActionBar = findViewById(R.id.postDetailsScreenTopActionBar)
        summeryTextView = findViewById(R.id.postDetailsScreenSummery)
        contentTextView = findViewById(R.id.postDetailsScreenContent)
        createdByText = findViewById(R.id.postDetailsCreatedBy)

        val fromPostList = intent

        topActionBar.setLeftButtonResource(R.drawable.back_icon_white)
        topActionBar.setLeftButtonOnClickListener {
            finish()
        }
        topActionBar.centerText = fromPostList.getStringExtra(POST_TITLE_KEY)
        createdByText.text = fromPostList.getStringExtra(POST_AUTHOR_KEY)
        summeryTextView.text = fromPostList.getStringExtra(POST_SUMMERY_KEY)
        contentTextView.text = fromPostList.getStringExtra(POST_CONTENT_KEY)
    }
}
