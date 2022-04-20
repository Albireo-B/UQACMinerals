package com.example.uqacminerals.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.uqacminerals.R
import com.example.uqacminerals.classes.MineralAdapter
import com.example.uqacminerals.database.MineralModel
import com.example.uqacminerals.ui.main.MainActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson


class ScannedMineralList : Fragment() {

    private val database = FirebaseDatabase.getInstance().reference

    private val scannedMineralList = ArrayList<MineralModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val mineralAdapter = MineralAdapter(requireContext(), scannedMineralList)

        setFragmentResultListener("Groupe") { Groupe, bundle ->
            val result = bundle.getInt("GroupeKey")
            //check if result is double/string/any for the "equalto" ?
            if (scannedMineralList.isEmpty()) {
                database.child("mineral").orderByChild("groupe").equalTo(result.toDouble()).get()
                    .addOnSuccessListener {
                        for (item in it.children) {
                            val mineralModel = MineralModel(
                                item.child("nom").value.toString(),
                                item.child("description").value.toString(),
                                item.child("image").value.toString(),
                                Integer.parseInt(item.child("groupe").value.toString())
                            )
                            if (!scannedMineralList.contains(mineralModel))
                                scannedMineralList.add(mineralModel)
                            Log.i(
                                "firebase",
                                "Objet : ${mineralModel.GetNom()},${mineralModel.GetDescription()},${mineralModel.GetImage()},${mineralModel.GetGroupe()}"
                            )
                        }

                        mineralAdapter.UpdateList(scannedMineralList)

                    }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wiki, container, false)
        val wikiListView : ListView = view.findViewById(R.id.listViewMinerals)

        wikiListView.adapter = mineralAdapter


        wikiListView.setOnItemClickListener { adapter, v, position, resource ->
            val itemClicked = adapter.getItemAtPosition(position) as MineralModel
            val gson = Gson()
            setFragmentResult("MineralClicked", bundleOf("MineralKey" to gson.toJson(itemClicked)))


            (activity as MainActivity).ChangeFragment("ScannedMineralList","MineralDetail")

        }

        // Inflate the layout for this fragment
        return view
    }

}