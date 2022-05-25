package com.example.cafe

data class MenuModel(
    var result: ArrayList<Result>
){
    data class Result(
        val id: Int,
        val nama: String,
        val deskripsi: String,
        val harga: Int,
        val image: String
        )
}
