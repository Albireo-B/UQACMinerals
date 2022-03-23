package com.example.uqacminerals.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.uqacminerals.R
import com.example.uqacminerals.classes.ViewPagerAdapter


class MainActivity : FragmentActivity() {

    private lateinit var appViewPager : ViewPager
    private val PERMISSION_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                123);

            /*ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);*/
        }
        else {

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appViewPager = findViewById(R.id.App_ViewPager)

        appViewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }


}