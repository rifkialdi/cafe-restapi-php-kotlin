package com.example.cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.list_menu.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class OrderActivity : AppCompatActivity() {

    private lateinit var nomorMeja: EditText
    private lateinit var menuId : TextView
    private lateinit var jmlOrder : EditText

    private fun initComponent(){
        nomorMeja = findViewById(R.id.etNomorMeja)
        menuId = findViewById(R.id.tvId)
        jmlOrder = findViewById(R.id.etJmlOrder)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
            initComponent()

        supportActionBar!!.title = "Order"

        val date_n = SimpleDateFormat("EEEE, dd-mm-yyyy", Locale.getDefault()).format(Date())
        val current = LocalDateTime.now()
        val formatted = current.format(DateTimeFormatter.ofPattern("HH:mm:ss"))

        tvDate.setText(date_n)
        tvClock.setText(formatted)

        tvId.text = intent.getStringExtra("intent_id")

        Glide.with(this)
            .load(intent.getStringExtra("intent_image"))
            .placeholder(R.drawable.resto)
            .error(R.drawable.resto)
            .centerCrop()
            .into(IVGambarMenu)

        tvNamaMenu.text = intent.getStringExtra("intent_nama")
        tvHargaMenu.text = intent.getStringExtra("intent_harga")

        btnKirim.setOnClickListener {
            addCart()
        }

        BtnCart.setOnClickListener{
            val intent = Intent(this,ViewCart::class.java)
            startActivity(intent)


        }

    }

    private fun addCart() {
        val nomorMeja = nomorMeja.text.toString()
        val jmlOrder = jmlOrder.text.toString()
        val menuId = menuId.text.toString()

        if (nomorMeja.trim()==""||
                jmlOrder.trim()=="")
        {
            Toast.makeText(this, "Nomor dan jumlah order masih kosong!!", Toast.LENGTH_SHORT).show()
        }
        else{
            var requestCall: Call<ResponseModel> = ApiService.endpoint
                .addCart(jmlOrder,
                    menuId.toInt(),
                    jmlOrder.toInt())
            requestCall.enqueue(object : Callback<ResponseModel>{
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    Log.d("response", response.body()!!.message)
                    if (response.isSuccessful){
                        Toast.makeText(this@OrderActivity, "Add Cart Successfully ", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@OrderActivity, "Add Cart Failed", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Toast.makeText(baseContext, "Add Cart Failed", Toast.LENGTH_SHORT).show()
                }
            }
            )
        }
    }
}