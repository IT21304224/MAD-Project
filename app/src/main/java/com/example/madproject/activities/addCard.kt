package com.example.madproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.models.CardModel
import com.example.madproject.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addCard : AppCompatActivity() {

    private lateinit var etCardType: EditText
    private lateinit var etCardNo: EditText
    private lateinit var etCHname: EditText
    private lateinit var etExpDate: EditText
    private lateinit var etCVCNo: EditText
    private lateinit var btnInsertData: Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        etCardType = findViewById(R.id.etCardType)
        etCardNo = findViewById(R.id.etCardNo)
        etCHname = findViewById(R.id.etCHname)
        etExpDate = findViewById(R.id.etExpDate)
        etCVCNo = findViewById(R.id.etCVCNo)
        btnInsertData = findViewById(R.id.btnUpdateData2)

        dbRef = FirebaseDatabase.getInstance().getReference("Card")

        btnInsertData.setOnClickListener{
            saveCardData()
        }
    }

    private fun saveCardData(){

        //getting values
        val CardType = etCardType.text.toString()
        val CardNo = etCardNo.text.toString()
        val CHname = etCHname.text.toString()
        val ExpDate = etExpDate.text.toString()
        val CVCNo = etCVCNo.text.toString()

        if(CardType.isEmpty()){
            etCardType.error = "Please enter card type"
        }
        if(CardNo.isEmpty()){
            etCardNo.error = "Please enter card number"
        }
        if(CHname.isEmpty()){
            etCHname.error = "Please enter card holder name"
        }
        if(ExpDate.isEmpty()){
            etExpDate.error = "Please enter expiry date"
        }
        if(CVCNo.isEmpty()){
            etCVCNo.error = "Please enter cvc number"
        }

        val cardId = dbRef.push().key!!

        val card = CardModel(cardId,CardType, CardNo, CHname, ExpDate, CVCNo)

        dbRef.child(cardId).setValue(card)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etCardType.text.clear()
                etCardNo.text.clear()
                etCHname.text.clear()
                etExpDate.text.clear()
                etCVCNo.text.clear()

            }.addOnFailureListener{err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}