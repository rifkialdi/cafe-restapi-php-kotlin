package com.example.cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_view_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCart : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cart)

        supportActionBar!!.title = "List"
        setupRecylerView()
        getDataFromApi()
    }

    private fun setupRecylerView() {
        cartAdapter = CartAdapter(arrayListOf())
        rvorder.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun getDataFromApi() {
        showLoading(true)
        ApiService.endpoint.getAllCart()
            .enqueue(object : Callback<CartModel> {
                override fun onResponse(call: Call<CartModel>, response: Response<CartModel>) {
                    if (response.isSuccessful)
                        showResult(response.body()!!)
                }

                override fun onFailure(call: Call<CartModel>, t: Throwable) {
                    showLoading(false)
                }

            })

    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> progressBar.visibility = View.GONE
            false -> progressBar.visibility = View.VISIBLE
        }
    }

    private fun showResult(results: CartModel) {
        cartAdapter.setData(results.result)
    }


}