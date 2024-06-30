package com.example.mymap

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymap.models.UserMap

private const val TAG ="MapsAdapter"
class MapsAdapter(val context: Context, val userMaps: List<UserMap>, val onClickListener: OnClickListener) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {



//define the interface
    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsAdapter.ViewHolder {
        val view=LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userMaps.size
    }

    override fun onBindViewHolder(holder: MapsAdapter.ViewHolder, position: Int) {
        val userMap= userMaps[position]
        holder.itemView.setOnClickListener {
            Log.i(TAG, "Tapped on psition $position")
            onClickListener.onItemClick(position)
        }

        val textViewTitle=holder.itemView.findViewById<TextView>(android.R.id.text1)
        textViewTitle.text=userMap.title
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


    }
}
