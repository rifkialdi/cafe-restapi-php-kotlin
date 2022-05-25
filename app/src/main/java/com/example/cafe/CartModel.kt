package com.example.cafe

data class CartModel(
    var result: ArrayList<Result>
){
    data class Result(
        val nomor_meja: String,
        val nama: String,
        val harga: Int,
        val jumlah_order: Int,
        val jumlah_harga: Int
    )
}