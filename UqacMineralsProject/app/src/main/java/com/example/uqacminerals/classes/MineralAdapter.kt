package com.example.uqacminerals.classes

import android.content.Context
import android.util.Log
import com.example.uqacminerals.database.MineralModel
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.uqacminerals.R
import android.widget.TextView
import java.util.ArrayList

class MineralAdapter(private val contexte : Context, private var mineralModelList: ArrayList<MineralModel>) :
    ArrayAdapter<MineralModel>(
        contexte, 0, mineralModelList
    ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null)
            listItem = LayoutInflater.from(contexte).inflate(R.layout.listview_item, parent, false)

        val currentMineral = mineralModelList[position]

        val image = listItem!!.findViewById<View>(R.id.listViewItem_mineralImage) as ImageView
        //image.setImageResource(currentMineral.GetImage());
        val name = listItem.findViewById<View>(R.id.listViewItem_mineralName) as TextView
        name.text = currentMineral.GetNom()

        return listItem
    }

    fun UpdateList(newMineralModelList: ArrayList<MineralModel>){
        mineralModelList = newMineralModelList
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): MineralModel {
        return mineralModelList[position]
    }

    override fun getCount(): Int {
        return mineralModelList.count()
    }

    fun GetMineralList() : ArrayList<MineralModel> {
        return mineralModelList
    }
}