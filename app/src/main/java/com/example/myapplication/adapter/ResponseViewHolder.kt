package com.example.myapplication.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.apiservices.MyResponse

class ResponseViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
    val imgResponse:ImageView  =itemView.findViewById(R.id.imgResponse)
    val tvName:TextView = itemView.findViewById(R.id.tvName)
    fun onBind(response: MyResponse) {
        tvName.text = response.name
        Glide.with(itemView).load(response.owner.avatarUrl).into(imgResponse)
    }
}