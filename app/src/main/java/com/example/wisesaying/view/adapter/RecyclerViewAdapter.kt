package com.example.wisesaying.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewholderBinding
import com.example.wisesaying.db.entity.Pigme

class RecyclerViewAdapter(var modelList: List<Pigme> = listOf()) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerviewholder,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return modelList.size
    }


//    Why????
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding?.pigme = modelList[position]
        holder.binding?.imageViewRecyclerView?.setImageResource(modelList[position].image)
        holder.binding?.textViewRecyclerView?.text = (modelList[position].textStory)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<RecyclerviewholderBinding>(view)
    }

}