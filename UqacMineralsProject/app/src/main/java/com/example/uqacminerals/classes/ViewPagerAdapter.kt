package com.example.uqacminerals.classes



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.uqacminerals.fragments.QRCode
import com.example.uqacminerals.fragments.Wiki

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return QRCode()
            1 -> return Wiki()
        }
        return QRCode()
    }


}