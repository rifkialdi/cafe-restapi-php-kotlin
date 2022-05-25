package com.example.cafe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "My Cafe"
        setupRecylerView()
        getDataFromApi()
    }
    private fun setupRecylerView(){
        menuAdapter =  MenuAdapter(arrayListOf(),object:
        MenuAdapter.OnAdapterListener{
            override fun onClick(result: MenuModel.Result) {
                startActivity(
                    Intent(this@MainActivity, OrderActivity::class.java)
                        .putExtra("intent_id", result.id.toString())
                        .putExtra("intent_image", result.image)
                        .putExtra("intent_nama", result.nama)
                        .putExtra("intent_harga", "Rp. ${result.harga.toString()}")
                )
                //Toast.makeText(applicationContext,result.nama, Toast.LENGTH_SHORT).show()
            }
        })
        rvMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuAdapter
        }
    }
    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.getMenu()
            .enqueue(object: Callback<MenuModel>{
                override fun onResponse(call: Call<MenuModel>, response: Response<MenuModel>) {
                    if(response.isSuccessful)
                        showResult(response.body()!!)
                }
                override fun onFailure(call: Call<MenuModel>, t: Throwable) {
                    showLoading(false)
                }
            })

    }
    private fun showLoading(loading: Boolean){
        when(loading){
            true -> progressBar.visibility = View.GONE
            false -> progressBar.visibility = View.VISIBLE
        }
    }

    private fun showResult(results: MenuModel) {
        menuAdapter.setData(results.result)
    }
}