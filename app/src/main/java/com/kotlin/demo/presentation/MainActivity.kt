package com.kotlin.demo.presentation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kotlin.demo.R
import com.kotlin.demo.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(), OnMainActivityCallback {

    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}