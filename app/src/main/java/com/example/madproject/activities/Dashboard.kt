package com.example.madproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.madproject.R

class Dashboard : AppCompatActivity() {

    private lateinit var btnCardDetails: Button
    private lateinit var ivHstore: ImageView
    private lateinit var ivTstore: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnCardDetails = findViewById(R.id.btnCardDetails)
        ivHstore = findViewById(R.id.ivHstore)
        ivTstore = findViewById(R.id.ivTstore)

        btnCardDetails.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        ivHstore.setOnClickListener{
            val intent = Intent(this, TotalAmount::class.java)
            startActivity(intent)
        }
        ivTstore.setOnClickListener{
            val intent = Intent(this, ThriftStore::class.java)
            startActivity(intent)
        }
    }
}