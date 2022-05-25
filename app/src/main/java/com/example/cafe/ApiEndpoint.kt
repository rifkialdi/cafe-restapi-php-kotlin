package com.example.cafe

import android.view.Menu
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {

    //fungsi getAllMenu yang ada pada file cafe.php
    @GET("myAPI.php?menu=getAllMenu")
    fun getMenu(): Call<MenuModel>

    @FormUrlEncoded
    @POST("myAPI.php?menu=addCart")
    fun addCart(
        @Field("nomor_meja") nomor_meja: String,
        @Field("menu_id") menu_id: Int,
        @Field("jumlah_order") jumlah_order: Int
    ): Call<ResponseModel>

    @GET("myAPI.php?menu=getAllCart")
    fun getAllCart(): Call<CartModel>
}