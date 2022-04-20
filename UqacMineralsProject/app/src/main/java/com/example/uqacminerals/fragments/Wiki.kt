package com.example.uqacminerals.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import com.example.uqacminerals.R
import com.example.uqacminerals.classes.MineralAdapter
import com.example.uqacminerals.database.MineralModel
import com.example.uqacminerals.ui.main.MainActivity
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.abs


class Wiki : Fragment(), GestureDetector.OnGestureListener {

    private val database = FirebaseDatabase.getInstance().reference

    private val mineralList = ArrayList<MineralModel>()
    // Declaring gesture detector
    private lateinit var gestureDetector: GestureDetector

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Initializing the gesture
        gestureDetector = GestureDetector(this)

        // this is the view we will add the gesture detector to
        val gestureView : View = requireView().findViewById(R.id.listViewMinerals)

        gestureView.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return gestureDetector.onTouchEvent(p1)
            }

        })

    }


    override fun onDown(p0: MotionEvent): Boolean {
        return true
    }
    override fun onShowPress(p0: MotionEvent) {
        return
    }
    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }
    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }
    override fun onLongPress(p0: MotionEvent) {
        return
    }
    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val swipeThreshold = 100
            val swipeVelocityThreshold = 100
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        (activity as MainActivity).ChangeFragment("Wiki","QRCode")
                        Log.e("SWIPE","swiping left to right")
                    }
                    else {
                        //Toast.makeText(context, "Right to Left swipe gesture", Toast.LENGTH_SHORT).show()
                        Log.e("SWIPE","swiping right to left")
                    }
                }
            }
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }


}