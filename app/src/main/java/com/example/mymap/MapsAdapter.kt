package com.example.mymap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymap.models.UserMap

class MapsAdapter(val context: Context, val userMaps: List<UserMap>) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsAdapter.ViewHolder {
        val view=LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userMaps.size
    }

    override fun onBindViewHolder(holder: MapsAdapter.ViewHolder, position: Int) {
        val userMap= userMaps[position]
        val textViewTitle=holder.itemView.findViewById<TextView>(android.R.id.text1)
        textViewTitle.text=userMap.title
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


    }
}
