package com.example.uqacminerals.classes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.uqacminerals.R
import com.example.uqacminerals.database.MineralModel
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


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
        image.setImageBitmap(getBitmapFromURL(currentMineral.GetImage()))
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

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url
                .openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}