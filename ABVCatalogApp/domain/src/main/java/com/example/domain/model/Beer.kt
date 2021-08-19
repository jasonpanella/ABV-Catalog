package com.example.domain.model.beer


data class Beer(
    val breweryId: Int,
    val name: String,
    val cat: Int,
    val style: Int,
    val abv: Float,
    val ibu: Float,
    val srm: Float,
    val upc: Int,
    val descript: String
)