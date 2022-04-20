package com.example.uqacminerals.database

class MineralModel(private val nom: String,private val description: String,private val image: String,private val groupe : Int) {


    fun GetNom() : String {
        return nom
    }
    fun GetDescription() : String {
        return description
    }
    fun GetImage() : String {
        return image
    }
    fun GetGroupe() : Int {
        return groupe
    }
}