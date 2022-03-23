package com.example.uqacminerals.ui.main

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.uqacminerals.R
import com.example.uqacminerals.classes.ViewPagerAdapter
import com.tbruyelle.rxpermissions2.RxPermissions


class MainActivity : FragmentActivity() {

    private lateinit var appViewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        val rxPermissions = RxPermissions(this)
        rxPermissions
            .request(Manifest.permission.CAMERA) // ask single or multiple permission once
            .subscribe { granted: Boolean ->
                if (granted) {
                    // All requested permissions are granted
                } else {
                    // At least one permission is denied
                }
            }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appViewPager = findViewById(R.id.App_ViewPager)

        appViewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }


}