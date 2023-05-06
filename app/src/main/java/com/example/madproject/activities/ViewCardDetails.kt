package com.example.madproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madproject.R
import com.example.madproject.models.CardModel
import com.google.firebase.database.FirebaseDatabase

class ViewCardDetails : AppCompatActivity() {
    private lateinit var etCardId: TextView
    private lateinit var etCardType: TextView
    private lateinit var etCardNo: TextView
    private lateinit var etCHName: TextView
    private lateinit var etExpDate: TextView
    private lateinit var etCVCNo: TextView
    private lateinit var btnUpdateData: Button
    private lateinit var btnDeleteData: Button
    private lateinit var btnBack1: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_card_details)

        initView()
        setValuesToView()

        btnBack1 = findViewById(R.id.btnBack2)

        btnBack1.setOnClickListener{
            val intent = Intent(this, FetchingCard::class.java)
            startActivity(intent)
        }

        btnUpdateData.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("cardId").toString(),
                intent.getStringExtra("CardType").toString()
            )
        }

        btnDeleteData.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("cardId").toString()
            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Card").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Card details deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this,FetchingCard::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error->
            Toast.makeText(this,"Deleting Error ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        etCardId = findViewById(R.id.etCardId)
        etCardType = findViewById(R.id.etCardType)
        etCardNo = findViewById(R.id.etCardNo)
        etCHName = findViewById(R.id.etCHname)
        etExpDate = findViewById(R.id.etExpDate)
        etCVCNo = findViewById(R.id.etCVCNo)

        btnUpdateData = findViewById(R.id.btnUpdateData2)
        btnDeleteData = findViewById(R.id.btnDeleteData)
    }

    private fun setValuesToView(){
        etCardId.text = intent.getStringExtra("cardId")
        etCardType.text = intent.getStringExtra("CardType")
        etCardNo.text = intent.getStringExtra("CardNo")
        etCHName.text = intent.getStringExtra("CHname")
        etExpDate.text = intent.getStringExtra("ExpDate")
        etCVCNo.text = intent.getStringExtra("CVCNo")

    }

    private fun openUpdateDialog(
        cardId: String,
        CardType: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_card,null)

        mDialog.setView(mDialogView)

        val ettCardType = mDialogView.findViewById<EditText>(R.id.etCardType)
        val ettCardNo = mDialogView.findViewById<EditText>(R.id.etCardNo)
        val ettCHname = mDialogView.findViewById<EditText>(R.id.etCHname)
        val ettExpDate = mDialogView.findViewById<EditText>(R.id.etExpDate)
        val ettCVCNo = mDialogView.findViewById<EditText>(R.id.etCVCNo)
        val btnUpdateData2 = mDialogView.findViewById<Button>(R.id.btnUpdateData2)

        ettCardType.setText(intent.getStringExtra("CardType").toString())
        ettCardNo.setText(intent.getStringExtra("CardNo").toString())
        ettCHname.setText(intent.getStringExtra("CHname").toString())
        ettExpDate.setText(intent.getStringExtra("ExpDate").toString())
        ettCVCNo.setText(intent.getStringExtra("CVCNo").toString())

        mDialog.setTitle("Updating $CardType Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData2.setOnClickListener{
            updateCardData(
                cardId,
                ettCardType.text.toString(),
                ettCardNo.text.toString(),
                ettCHname.text.toString(),
                ettExpDate.text.toString(),
                ettCVCNo.text.toString()

            )

            Toast.makeText(applicationContext, "Card Details Updated",Toast.LENGTH_LONG).show()

            //setting updated data to text views
            etCardType.text = ettCardType.text.toString()
            etCardNo.text = ettCardNo.text.toString()
            etCHName.text = ettCHname.text.toString()
            etExpDate.text = ettExpDate.text.toString()
            etCVCNo.text = ettCVCNo.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateCardData(
        id:String,
        type:String,
        cardno: String,
        chname:String,
        expdate:String,
        cvcno:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Card").child(id)
        val cardinfo = CardModel(id,type,cardno,chname,expdate,cvcno)
        dbRef.setValue(cardinfo)
    }
}