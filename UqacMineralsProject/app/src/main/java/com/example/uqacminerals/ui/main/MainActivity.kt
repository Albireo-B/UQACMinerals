package com.example.uqacminerals.ui.main

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import com.example.uqacminerals.R
import com.example.uqacminerals.fragments.MineralDetail
import com.example.uqacminerals.fragments.QRCode
import com.example.uqacminerals.fragments.ScannedMineralList
import com.example.uqacminerals.fragments.Wiki
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


class MainActivity : FragmentActivity() {

    private val mineralDetail = MineralDetail()
    private val scannedMineralList = ScannedMineralList()
    private var QRCode = QRCode()
    private val wiki = Wiki()
    private lateinit var currentFragment : Fragment
    private lateinit var OldFragment : Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                123
            );
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragments()

}


    private fun initFragments() {
        currentFragment = QRCode
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container_view, QRCode)
            .add(R.id.fragment_container_view, mineralDetail)
            .add(R.id.fragment_container_view, scannedMineralList)
            .add(R.id.fragment_container_view, wiki)
            .hide(mineralDetail)
            .hide(scannedMineralList)
            .hide(wiki)
            .show(QRCode)
            .commit()
    }

    fun GetFragmentFromName(name : String) : Fragment {
        return when (name) {
            "QRCode" -> QRCode
            "ScannedMineralList" -> scannedMineralList
            "Wiki" -> wiki
            "MineralDetail" -> mineralDetail
            else -> {
                QRCode
            }
        }
    }

    fun ChangeFragment(oldFragment : String, newFragment : String){
        currentFragment = GetFragmentFromName(newFragment)
        OldFragment = GetFragmentFromName(oldFragment)
        Log.e("FRAGMENT",newFragment)
        if (newFragment == "Wiki") {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .show(GetFragmentFromName(newFragment))
                .hide(GetFragmentFromName(oldFragment))
                .commit()
        } else if ( newFragment == "QRCode" && oldFragment == "ScannedMineralList"){
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .show(GetFragmentFromName(newFragment))
                .hide(GetFragmentFromName(oldFragment))
                .commit()

        } else {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .show(GetFragmentFromName(newFragment))
                .hide(GetFragmentFromName(oldFragment))
                .commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentFragment == GetFragmentFromName("ScannedMineralList")){
                    ChangeFragment("ScannedMineralList", "QRCode")
                    supportFragmentManager.beginTransaction().detach(currentFragment).commitNow()
                    supportFragmentManager.beginTransaction().attach(currentFragment).commitNow();
                    return true;
                }
            else if (currentFragment == GetFragmentFromName("MineralDetail") && OldFragment == GetFragmentFromName("Wiki")){
                ChangeFragment("MineralDetail", "Wiki")
                return true;
            }
            else if (currentFragment == GetFragmentFromName("MineralDetail") && OldFragment == GetFragmentFromName("ScannedMineralList")) {
                ChangeFragment("MineralDetail", "ScannedMineralList")
                return true;
            } else if (currentFragment == GetFragmentFromName("Wiki") || currentFragment == GetFragmentFromName("QRCode")){
                finishAffinity()
            }
        }
        return super.onKeyDown(keyCode, event)
    }


}