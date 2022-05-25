package com.example.cafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_cart.view.*



class CartAdapter(
    var results: ArrayList<CartModel.Result>):
    RecyclerView.Adapter<CartAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MyViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.list_cart,parent,false)
    )
    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = results[position]
        holder.view.tvNoMejaOrder.text = result.nomor_meja
        holder.view.tvMenuOrder.text = result.nama
        holder.view.tvHargaOrder.text = "Rp. ${result.harga.toString()}"
        holder.view.tvJumlahOrder.text = result.jumlah_order.toString()
        holder.view.tvSTotalOrder.text = result.jumlah_harga.toString()



    }

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: List<CartModel.Result>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }
}