package com.example.madproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.madproject.R
import androidx.appcompat.app.AlertDialog

class TotalAmount : AppCompatActivity() {

    private lateinit var etItemAmount: EditText
    private lateinit var etDeliveryFee: TextView
    private lateinit var etTotAmount: TextView
    private lateinit var btnPayok: Button
    private lateinit var btnCancel: Button
    private lateinit var btncalc:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_amount)

        etItemAmount = findViewById(R.id.etItemAmount)
        etDeliveryFee = findViewById(R.id.etDeliveryFee)
        etTotAmount = findViewById(R.id.etTotAmount)
        btnPayok = findViewById(R.id.btnPayok)
        btnCancel = findViewById(R.id.btnCancel)
        btncalc = findViewById(R.id.btncalc)

        btnPayok.setOnClickListener{
            val intent = Intent(this, PaymentSuccess::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener{
            val intent = Intent(this, ThriftStore::class.java)
            startActivity(intent)
        }

        var delfee = 300



        btncalc.setOnClickListener{
            val res1 = etItemAmount.text.toString().toInt()


            if (res1 > 2000) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Free Delivery")
                    .setMessage("Since Your bill amount is more than Rs.2500, you will not be charged a delivery fee.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()

                    }
                    .create()

                alertDialog.show()
                delfee = 0
            }else{
                delfee = 300
            }

            etDeliveryFee.text = delfee.toString()
            val res2 = etDeliveryFee.text.toString().toInt()
            val result = res1+res2
            etTotAmount.text = result.toString()
        }
    }

}