package com.benohayon.meallennium.framework.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object MealenniumFragmentManager {

    fun moveToFragment(activity: FragmentActivity, fragment: Fragment, containerRes: Int) {
        activity.supportFragmentManager.beginTransaction()
                .replace(containerRes, fragment)
                .addToBackStack(null)
                .commit()
    }

    fun popFragment(activity: FragmentActivity) {
        activity.supportFragmentManager.popBackStack()
    }

}