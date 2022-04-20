package com.example.uqacminerals.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uqacminerals.R
import com.example.uqacminerals.fragments.MineralDetail
import com.example.uqacminerals.fragments.QRCode
import com.example.uqacminerals.fragments.ScannedMineralList
import com.example.uqacminerals.fragments.Wiki
import kotlin.math.abs
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity


class MainActivity : FragmentActivity() {

    private val mineralDetail = MineralDetail()
    private val scannedMineralList = ScannedMineralList()
    private val QRCode = QRCode()
    private val wiki = Wiki()


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

    fun GetFragmentFromName(name : String) : Fragment{
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
        Log.e("FRAGMENT","Changement de fragment")
        if (newFragment == "Wiki" || newFragment == "QRCode") {
            Log.e("FRAGMENT","Nouveau fragment wiki")
            supportFragmentManager.beginTransaction()
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


}