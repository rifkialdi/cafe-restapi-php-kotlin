package com.example.cafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_menu.view.*

class MenuAdapter (
    var results: ArrayList<MenuModel.Result>,
    val listener: OnAdapterListener):
    RecyclerView.Adapter<MenuAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MyViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.list_menu,parent,false)
    )
    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = results[position]
        holder.view.tvNama.text = result.nama
        holder.view.tvDesc.text = result.deskripsi
        holder.view.tvHarga.text = "Rp. ${result.harga.toString()}"

        Glide.with(holder.view)
            .load(result.image)
            .placeholder(R.drawable.resto)
            .error(R.drawable.resto)
            .centerCrop()
            .into(holder.view.ivMenu)

        holder.view.setOnClickListener{listener.onClick(result)}
    }

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)

    interface OnAdapterListener{
        fun onClick(result: MenuModel.Result)
    }

    fun setData(data: List<MenuModel.Result>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }
}
