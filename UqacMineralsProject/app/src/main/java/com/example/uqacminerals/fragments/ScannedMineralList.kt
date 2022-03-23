package com.example.uqacminerals.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.uqacminerals.R


class ScannedMineralList : Fragment() {

    val scannedMinerals = arrayOf(
        "Almandin", "Amazonite",
        "Lépidolite"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wiki, container, false)
        val wikiListView : ListView = view.findViewById(R.id.listViewMinerals)
        val arr: ArrayAdapter<String> = ArrayAdapter(
            view.context,
            R.layout.listview_item,
            R.id.listViewItem_mineralName,
            scannedMinerals
        )
        wikiListView.adapter = arr

        // Inflate the layout for this fragment
        return view
    }

}