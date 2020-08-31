package com.benohayon.meallennium.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.interfaces.OnAnimationFinishListener
import com.benohayon.meallennium.framework.managers.FirebaseManager
import com.benohayon.meallennium.framework.managers.UserManager
import com.benohayon.meallennium.framework.models.CREATE_POST_REQUEST
import com.benohayon.meallennium.framework.models.POST_LIST_ACTIVITY_SEARCH_BOX_ANIMATION_DURATION
import com.benohayon.meallennium.framework.utils.MealenniumFragmentManager
import com.benohayon.meallennium.framework.utils.PopupManager
import com.benohayon.meallennium.ui.activities.abs.BaseActivity
import com.benohayon.meallennium.ui.custom_views.TopActionBar
import com.benohayon.meallennium.ui.fragments.MyPostsFragment
import com.benohayon.meallennium.ui.fragments.PostListFragment
import com.benohayon.meallennium.ui.fragments.SettingsFragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_post_list

    private lateinit var topActionBar: TopActionBar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchBoxFrame: View
    private lateinit var searchBox: View
    private lateinit var searchBoxEditText: EditText
    private lateinit var searchBoxCloseButton: ImageView
    private lateinit var addPostFab: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        MealenniumFragmentManager.moveToFragment(this, PostListFragment(), R.id.postListActivityContainer)
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
        initTopBar()
        initNavigationDrawer()
        progressBar = findViewById(R.id.postListActivityProgressBar)
        addPostFab = findViewById(R.id.postListFragmentAddPostFab)
        addPostFab.setOnClickListener {
            startActivityForResult(Intent(this, AddPostActivity::class.java), CREATE_POST_REQUEST)
        }
    }

    private fun initTopBar() {
        topActionBar = findViewById(R.id.postListActivityTopBar)
        topActionBar.setLeftButtonResource(R.drawable.navigation_drawer_icon)
        topActionBar.setRightButtonResource(R.drawable.search_white_icon)
        topActionBar.setLeftButtonOnClickListener {
            toggleDrawer()
        }
        topActionBar.setRightButtonOnClickListener {
            openSearchBox()
        }
        topActionBar.centerText = getString(R.string.top_action_bar_center_text)

        searchBoxFrame = findViewById(R.id.topActionBarSearchBoxFrame)
        searchBox = findViewById(R.id.topActionBarSearchBox)
        searchBoxCloseButton = findViewById(R.id.topActionBarSearchBoxCloseButton)
        searchBoxEditText = findViewById(R.id.topActionBarSearchBoxEditText)
        searchBoxCloseButton.setOnClickListener {
            closeSearchBox()
        }

        topActionBar.setSearchBoxSearchListener(TextView.OnEditorActionListener { v, actionId, event ->
            // write the search logic here
            true
        })
    }

    private fun initNavigationDrawer() {
        drawerLayout = findViewById(R.id.postListActivityDrawerLayout)
        navigationView = findViewById(R.id.postListActivityNavigationView)

        val userEmailTextView: TextView = navigationView.getHeaderView(0).findViewById(R.id.navigationDrawerHeadlineUserEmail)
        val userNameTextView: TextView = navigationView.getHeaderView(0).findViewById(R.id.navigationDrawerHeadlineUserName)
        userEmailTextView.text = UserManager.getEmail(this)
        userNameTextView.text = UserManager.getName(this)

        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigationDrawerMenuHomeOption -> {
                    MealenniumFragmentManager.moveToFragment(this, PostListFragment(), R.id.postListActivityContainer)
                    closeDrawer()
                }

                R.id.navigationDrawerMenuMyPostsOption -> {
                    MealenniumFragmentManager.moveToFragment(this, MyPostsFragment(), R.id.postListActivityContainer)
                    closeDrawer()
                }

                R.id.navigationDrawerMenuSettingsOption -> {
                    MealenniumFragmentManager.moveToFragment(this, SettingsFragment(), R.id.postListActivityContainer)
                    closeDrawer()
                }

                R.id.navigationDrawerMenuSignOutOption -> requestSignOut()

                R.id.navigationDrawerMenuAboutOption -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    closeDrawer()
                }
            }

            true
        }

        navigationView.setCheckedItem(R.id.navigationDrawerMenuHomeOption)
    }

    private fun requestSignOut() {
        PopupManager.showConfirmationPopup(this, getString(R.string.alert_sign_out_title), getString(R.string.alert_sign_out_message),
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

    private fun openSearchBox() {
        topActionBar.disableRightButton()
        searchBox.alpha = 1f
        ScaleAnimation(0f, searchBoxFrame.measuredWidth.toFloat(), 1f, 1f).apply {
            duration = POST_LIST_ACTIVITY_SEARCH_BOX_ANIMATION_DURATION
            setAnimationListener(object : OnAnimationFinishListener() {
                override fun onAnimationEnd(animation: Animation?) {
                    searchBox.layoutParams = FrameLayout.LayoutParams(searchBoxFrame.measuredWidth, convertDpToPixel(70f, this@HomeActivity).toInt())
                    searchBoxCloseButton.visibility = View.VISIBLE
                    searchBoxCloseButton.isClickable = true
                    searchBoxEditText.requestFocus()
                    openKeyboard()
                }
            })
            searchBox.startAnimation(this)
        }
    }

    private fun closeSearchBox() {
        searchBoxEditText.setText("")
        searchBoxCloseButton.isClickable = false
        searchBoxCloseButton.visibility = View.INVISIBLE
        ScaleAnimation(1f, 0f, 1f, 1f).apply {
            duration = POST_LIST_ACTIVITY_SEARCH_BOX_ANIMATION_DURATION
            setAnimationListener(object : OnAnimationFinishListener() {
                override fun onAnimationEnd(animation: Animation?) {
                    searchBox.layoutParams = FrameLayout.LayoutParams(1, convertDpToPixel(70f, this@HomeActivity).toInt())
                    searchBox.alpha = 0f
                    topActionBar.enableRightButton()
                    closeKeyboard()
                }
            })
            searchBox.startAnimation(this)
        }
    }

    private fun openKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(0, 0)
    }

    private fun closeKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(0, 0)
    }

    private fun toggleDrawer() {
        if (isDrawerOpen())
            closeDrawer()
        else
            openDrawer()
    }

    private fun requestExit() {
        PopupManager.showConfirmationPopup(this, getString(R.string.alert_exit_title), getString(R.string.alert_exit_message),
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

    private fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources).displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT
    }
}