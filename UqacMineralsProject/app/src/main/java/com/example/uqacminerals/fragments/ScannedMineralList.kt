package com.example.uqacminerals.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.uqacminerals.R
import com.example.uqacminerals.classes.MineralAdapter
import com.example.uqacminerals.database.MineralModel
import com.example.uqacminerals.ui.main.MainActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson


class ScannedMineralList : Fragment() {

    private val database = FirebaseDatabase.getInstance().reference

    private val scannedMineralList = ArrayList<MineralModel>()

    private lateinit var mineralAdapter : MineralAdapter
    private lateinit var wikiListView : ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wiki, container, false)

        mineralAdapter = MineralAdapter(requireContext(), scannedMineralList)

        setFragmentResultListener("Groupe") { Groupe, bundle ->
            val result = bundle.getInt("GroupeKey")


            database.child("mineral").orderByChild("groupe").equalTo(result.toDouble()).get()
                .addOnSuccessListener {
                    val firebaseMineralStorage: StorageReference = FirebaseStorage.getInstance().reference.child("mineral-image-firebase")

                    scannedMineralList.clear()
                    mineralAdapter.UpdateList(scannedMineralList)

                    for (item in it.children) {

                        firebaseMineralStorage
                            .child(item.child("nom").value.toString() + ".png")
                            .downloadUrl.addOnSuccessListener {

                                val mineralModel = MineralModel(
                                    item.child("nom").value.toString(),
                                    item.child("description").value.toString(),
                                    it.toString(),
                                    Integer.parseInt(item.child("groupe").value.toString())
                                )


                                if (!scannedMineralList.contains(mineralModel))
                                    scannedMineralList.add(mineralModel)

                                Log.e(
                                    "firebase",
                                    "Objet : ${mineralModel.GetNom()},${mineralModel.GetDescription()},${mineralModel.GetImage()},${mineralModel.GetGroupe()}"
                                )
                                mineralAdapter.UpdateList(scannedMineralList)
                            }
                    }

                }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }

        }




        wikiListView = view.findViewById(R.id.listViewMinerals)

        wikiListView.adapter = mineralAdapter



        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val searchView : SearchView = view.findViewById(R.id.wiki_searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("SEARCH","search submitted")
                val searchMineralList = ArrayList<MineralModel>()
                scannedMineralList.forEach{
                    if (it.GetNom().equals(query.trim(), true)){
                        searchMineralList.add(it)
                    }
                }
                Log.e("SEARCH",searchMineralList.toString())
                if (searchMineralList.isNotEmpty()) {
                    mineralAdapter.UpdateList(searchMineralList)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.trim() == ""){
                    Log.e("SEARCH","Resetting mineralList search bar empty")
                    mineralAdapter.UpdateList(scannedMineralList)
                }
                return false
            }
        })

        wikiListView.setOnItemClickListener { adapter, v, position, resource ->
            val itemClicked = mineralAdapter.getItem(position)
            val gson = Gson()
            setFragmentResult("MineralClicked", bundleOf("MineralKey" to gson.toJson(itemClicked)))


            (activity as MainActivity).ChangeFragment("ScannedMineralList","MineralDetail")

        }

    }




}