package com.example.uqacminerals.classes



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.uqacminerals.fragments.MineralDetail
import com.example.uqacminerals.fragments.QRCode
import com.example.uqacminerals.fragments.ScannedMineralList
import com.example.uqacminerals.fragments.Wiki

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 4;
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return QRCode()
            1 -> return Wiki()
            2 -> return ScannedMineralList()
            3 -> return MineralDetail()
        }
        return QRCode()
    }


}