package com.example.uqacminerals.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.uqacminerals.R
import com.example.uqacminerals.classes.MineralAdapter
import com.example.uqacminerals.database.MineralModel
import com.google.firebase.database.FirebaseDatabase


class Wiki : Fragment() {

    private val database = FirebaseDatabase.getInstance().reference

    private val mineralList = ArrayList<MineralModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_wiki, container, false)
        val wikiListView : ListView = view.findViewById(R.id.listViewMinerals)
        val mineralAdapter = MineralAdapter(requireContext(), mineralList)

        if (mineralList.isEmpty()) {
            database.child("mineral").get().addOnSuccessListener {
                for (item in it.children){
                    val mineralModel = MineralModel(item.child("nom").value.toString(),item.child("description").value.toString(),item.child("image").value.toString(),Integer.parseInt(item.child("groupe").value.toString()))
                    if (!mineralList.contains(mineralModel))
                        mineralList.add(mineralModel)
                    Log.i("firebase", "Objet : ${mineralModel.GetNom()},${mineralModel.GetDescription()},${mineralModel.GetImage()},${mineralModel.GetGroupe()}")
                }

                mineralAdapter.UpdateList(mineralList)

            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        wikiListView.adapter = mineralAdapter
        // Inflate the layout for this fragment
        return view
    }

}