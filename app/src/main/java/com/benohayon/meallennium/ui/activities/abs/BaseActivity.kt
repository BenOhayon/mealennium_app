package com.benohayon.meallennium.ui.activities.abs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutResource: Int

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
    }

    fun startActivityAndFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }
}
