package com.example.uqacminerals.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.uqacminerals.R


class Wiki : Fragment() {


    val minerals = arrayOf(
        "Almandin", "Amazonite",
        "Lépidolite", "Chlorite",
        "Epidote", "Fluorite",
        "Zircon", "Quartz",
        "Pyrite"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wiki, container, false)
        val wikiListView : ListView = view.findViewById(R.id.listViewMinerals)
        val arr: ArrayAdapter<String> = ArrayAdapter(
            view.context,
            R.layout.listview_item,
            R.id.listViewItem_mineralName,
            minerals
        )
        wikiListView.adapter = arr

        // Inflate the layout for this fragment
        return view
    }

}