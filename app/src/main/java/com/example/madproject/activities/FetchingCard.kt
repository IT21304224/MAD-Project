package com.example.madproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.adapters.CardAdapter
import com.example.madproject.models.CardModel
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FetchingCard : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var searchView: SearchView
    private lateinit var cardList: ArrayList<CardModel>
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_card)

        cardRecyclerView = findViewById(R.id.rvCard)
        cardRecyclerView.layoutManager = LinearLayoutManager(this)
        cardRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        searchView = findViewById(R.id.searchView)

        cardList = arrayListOf<CardModel>()

        // set up the SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Search for classes"

        // get data from Firebase
        getCardData()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!TextUtils.isEmpty(newText)) {
            search(newText!!)
        } else {
            getCardData()
        }
        return true
    }

    private fun search(query: String) {
        val searchResultList = arrayListOf<CardModel>()
        for (CardModel in cardList) {
            if (CardModel.CardNo?.toLowerCase(Locale.ROOT)?.contains(query.toLowerCase()) == true) {
                searchResultList.add(CardModel)
            }
        }
        val cAdapter = CardAdapter(searchResultList)
        cardRecyclerView.adapter = cAdapter
        cAdapter.setOnItemClickListner(object : CardAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@FetchingCard, ViewCardDetails::class.java)
                //put extras
                intent.putExtra("cardId", searchResultList[position].cardId)
                intent.putExtra("CardType", searchResultList[position].CardType)
                intent.putExtra("CardNo", searchResultList[position].CardNo)
                intent.putExtra("CHname", searchResultList[position].CHname)
                intent.putExtra("ExpDate", searchResultList[position].ExpDate)
                intent.putExtra("CVCNo", searchResultList[position].CVCNo)
                startActivity(intent)
            }
        })
    }


    private fun getCardData(){
        cardRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Card")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                cardList.clear()
                if(snapshot.exists()){
                    for(cardSnap in snapshot.children){
                        val cardData = cardSnap.getValue(CardModel::class.java)
                        cardList.add(cardData!!)
                    }

                    val cAdapter = CardAdapter(cardList)
                    cardRecyclerView.adapter=cAdapter

                    cAdapter.setOnItemClickListner(object : CardAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingCard, ViewCardDetails::class.java)

                            //put extras
                            intent.putExtra("cardId",cardList[position].cardId)
                            intent.putExtra("CardType",cardList[position].CardType)
                            intent.putExtra("CardNo",cardList[position].CardNo)
                            intent.putExtra("CHname",cardList[position].CHname)
                            intent.putExtra("ExpDate",cardList[position].ExpDate)
                            intent.putExtra("CVCNo",cardList[position].CVCNo)
                            startActivity(intent)
                        }
                    })
                }else {
                    tvLoadingData.text = "no"
                }
                    cardRecyclerView.visibility= View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }


            override fun onCancelled(error: DatabaseError) {
                tvLoadingData.text = "Error: ${error.message}"
                tvLoadingData.visibility = View.VISIBLE
                cardRecyclerView.visibility = View.GONE
            }

        })
    }
}