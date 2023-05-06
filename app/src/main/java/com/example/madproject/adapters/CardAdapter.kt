package com.example.madproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.models.CardModel

class CardAdapter(private val cardList: ArrayList<CardModel>):
    RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    private lateinit var cListner: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListener: onItemClickListener){
        cListner = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.card_list_item,parent,false)
        return ViewHolder(itemView, cListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCard = cardList[position]
        holder.tvCardName.text = currentCard.CardNo
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    class ViewHolder (itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val tvCardName : TextView = itemView.findViewById(R.id.tvCardName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}