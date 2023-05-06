package com.example.madproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madproject.R
import com.example.madproject.models.CardModel
import com.example.madproject.models.DeliveryModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddDelivery : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPhoneNo: EditText
    private lateinit var etAddress1: EditText
    private lateinit var etAddress2: EditText
    private lateinit var etCity: EditText
    private lateinit var etState: EditText
    private lateinit var etZipCode: EditText
    private lateinit var btnNext: Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_delivery)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etPhoneNo = findViewById(R.id.etPhoneNo)
        etAddress1 = findViewById(R.id.etAddress1)
        etAddress2 = findViewById(R.id.etAddress2)
        etCity = findViewById(R.id.etCity)
        etState = findViewById(R.id.etState)
        etZipCode = findViewById(R.id.etZipCode)
        btnNext = findViewById(R.id.btnNext)

        dbRef = FirebaseDatabase.getInstance().getReference("Delivery")

        btnNext.setOnClickListener{
            saveDeliveryData()
            val intent = Intent(this, TotalAmount::class.java)
            startActivity(intent)
        }
    }

    private fun saveDeliveryData(){

        //getting values
        val FirstName = etFirstName.text.toString()
        val LastName = etLastName.text.toString()
        val PhoneNo = etPhoneNo.text.toString()
        val Address1 = etAddress1.text.toString()
        val Address2 = etAddress2.text.toString()
        val City = etCity.text.toString()
        val State = etState.text.toString()
        val ZipCode = etZipCode.text.toString()

        if(FirstName.isEmpty()){
            etFirstName.error = "Please enter First Name"
        }
        if(LastName.isEmpty()){
            etLastName.error = "Please enter Last Name"
        }
        if(PhoneNo.isEmpty()){
            etPhoneNo.error = "Please enter Phone Number"
        }
        if(Address1.isEmpty()){
            etAddress1.error = "Please enter Street Address 1"
        }
        if(Address2.isEmpty()){
            etAddress2.error = "Please enter Street Address 2"
        }
        if(City.isEmpty()){
            etCity.error = "Please enter City"
        }
        if(State.isEmpty()){
            etState.error = "Please enter State"
        }
        if(ZipCode.isEmpty()){
            etZipCode.error = "Please enter Zip Code"
        }

        val deliveryId = dbRef.push().key!!

        val Delivery = DeliveryModel(deliveryId,FirstName, LastName, PhoneNo, Address1, Address2, City,State,ZipCode)

        dbRef.child(deliveryId).setValue(Delivery)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etFirstName.text.clear()
                etLastName.text.clear()
                etPhoneNo.text.clear()
                etAddress1.text.clear()
                etAddress2.text.clear()
                etCity.text.clear()
                etState.text.clear()
                etZipCode.text.clear()

            }.addOnFailureListener{err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}