package com.example.uqacminerals.ui.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.uqacminerals.R
import com.example.uqacminerals.classes.ViewPagerAdapter

class MainActivity : FragmentActivity() {

    private lateinit var appViewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appViewPager = findViewById(R.id.App_ViewPager)

        appViewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }

}