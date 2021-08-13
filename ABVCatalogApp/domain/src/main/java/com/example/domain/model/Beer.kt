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
){
//    override fun toString(): String {
//        return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", age=" + age + "]"
//    }
}
//
//val name: String,
//val cat: Int,
//val style: Int,
//val abv: Float,
//val ibu: Float,
//val srm: Float,
//val upc: Int,
//val filepath: String,
//val descript: String,
//val add_user: Int
