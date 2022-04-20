package com.example.uqacminerals.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import com.example.uqacminerals.R
import com.example.uqacminerals.database.MineralModel
import com.google.gson.Gson


class MineralDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setFragmentResultListener("MineralClicked") { Groupe, bundle ->

            val result = bundle.getString("MineralKey")
            val gson = Gson()
            val mineral = gson.fromJson(result, MineralModel::class.java)

            val nameView: TextView = requireView().findViewById(R.id.mineralDetail_Nom) as TextView
            val descriptionView = requireView().findViewById(R.id.mineralDetail_Description) as TextView
            val imageView = requireView().findViewById(R.id.mineralDetail_Image) as ImageView

            nameView.text = mineral.GetNom()
            descriptionView.text = mineral.GetDescription()
            //imageView.setImageURI(mineral.GetImage())

        }


        return inflater.inflate(R.layout.fragment_mineral_detail, container, false)
    }

}