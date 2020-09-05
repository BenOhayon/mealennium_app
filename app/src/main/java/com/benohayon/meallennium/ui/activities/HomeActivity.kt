package com.benohayon.meallennium.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.benohayon.meallennium.R
import com.benohayon.meallennium.createCustomViewForActionBar
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.managers.UserManager
import com.benohayon.meallennium.framework.models.CREATE_POST_REQUEST
import com.benohayon.meallennium.framework.utils.MealenniumFragmentManager
import com.benohayon.meallennium.framework.utils.MealenniumPopupManager
import com.benohayon.meallennium.ui.activities.abs.BaseActivity
import com.benohayon.meallennium.ui.fragments.MyPostsFragment
import com.benohayon.meallennium.ui.fragments.PostListFragment

class HomeActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_home

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var addPostFab: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        MealenniumFragmentManager.openFragment(this, PostListFragment(), R.id.postListActivityContainer)
    }

    override fun onResume() {
        super.onResume()
        updateNavigationDrawerHeaderDetails()
    }

    private fun initActionBar() {
        title = getString(R.string.mealennium_top_action_bar_center_text)
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon)
        supportActionBar?.customView = createCustomViewForActionBar(this, title)
    }

    override fun onBackPressed() {
        if (isDrawerOpen())
            closeDrawer()
        else
            requestExit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CREATE_POST_REQUEST) {
            if (resultCode == RESULT_OK) {
                // do operation after post is created
                Toast.makeText(this, "Post Created!", Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initUI() {
        initActionBar()
        initNavigationDrawer()
        progressBar = findViewById(R.id.postListActivityProgressBar)
        addPostFab = findViewById<ImageView>(R.id.postListFragmentAddPostFab).apply {
            setOnClickListener {
                startActivityForResult(Intent(this@HomeActivity, AddPostActivity::class.java), CREATE_POST_REQUEST)
            }
        }
    }

    private fun updateNavigationDrawerHeaderDetails() {
        val userEmailTextView: TextView = findViewById(R.id.navigationDrawerHeadlineUserEmail)
        val userNameTextView: TextView = findViewById(R.id.navigationDrawerHeadlineUserName)
        userEmailTextView.text = UserManager.getEmail(this)
        userNameTextView.text = UserManager.getName(this)
    }

    private fun initNavigationDrawer() {
        drawerLayout = findViewById(R.id.postListActivityDrawerLayout)
        updateNavigationDrawerHeaderDetails()

        findViewById<View>(R.id.navigationDrawerMenuHomeOption).setOnClickListener {
            closeDrawer()
            MealenniumFragmentManager.openFragment(this, PostListFragment(), R.id.postListActivityContainer)
        }

        findViewById<View>(R.id.navigationDrawerMenuMyPostsOption).setOnClickListener {
            closeDrawer()
            MealenniumFragmentManager.openFragment(this, MyPostsFragment(), R.id.postListActivityContainer)
        }

        findViewById<View>(R.id.navigationDrawerMenuSettingsOption).setOnClickListener {
            openSettingsActivity()
            closeDrawer()
        }

        findViewById<View>(R.id.navigationDrawerMenuSignOutOption).setOnClickListener {
            requestSignOut()
        }
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun requestSignOut() {
        MealenniumPopupManager.showConfirmationPopup(this, getString(R.string.mealennium_alert_sign_out_title), getString(R.string.mealennium_alert_sign_out_message),
                onYesClick = { _, _ ->
                    progressBar.visibility = View.VISIBLE
                    drawerLayout.closeDrawer(GravityCompat.START)
                    FirebaseManager.signOut(this) {
                        progressBar.visibility = View.INVISIBLE
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> toggleDrawer()
        }

        return true
    }

    private fun toggleDrawer() {
        if (isDrawerOpen()) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }

    private fun requestExit() {
        MealenniumPopupManager.showConfirmationPopup(this, getString(R.string.mealennium_alert_exit_title), getString(R.string.mealennium_alert_exit_message),
                onYesClick = { _, _ ->
                    finish()
                })
    }

    private fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun isDrawerOpen(): Boolean {
        return drawerLayout.isDrawerOpen(GravityCompat.START)
    }
}