package com.example.myapplication.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.myapplication.apiservices.MyResponse
import android.view.LayoutInflater
import android.widget.Filter
import android.widget.Filterable
import com.example.myapplication.R


class ResponseAdapter(private val context: Context,val arrayList: ArrayList<MyResponse>) : RecyclerView.Adapter<ResponseViewHolder>(),
    Filterable {
    var arrayListFiltered: ArrayList<MyResponse> = ArrayList()
    init {
//        arrayListFiltered.clear()
        arrayListFiltered.addAll(arrayList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ResponseViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.view_my_response, parent, false)
        return ResponseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrayListFiltered.size
    }

    override fun onBindViewHolder(p0: ResponseViewHolder, p1: Int) {
        p0.onBind(arrayListFiltered[p1])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                arrayListFiltered.clear()
                if (charString.isEmpty()) {
                    arrayListFiltered.addAll(arrayList)
                } else {
                    for (row in arrayList) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            arrayListFiltered.add(row)
                        }
                    }
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = arrayListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                arrayListFiltered = filterResults.values as ArrayList<MyResponse>
                notifyDataSetChanged()
            }
        }
    }
}