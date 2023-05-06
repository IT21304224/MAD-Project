package com.example.madproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.madproject.R

class ThriftStore : AppCompatActivity() {

    private lateinit var buy: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thrift_store)

        buy = findViewById(R.id.buy)

        buy.setOnClickListener{
            val intent = Intent(this, AddDelivery::class.java)
            startActivity(intent)
        }

    }
}