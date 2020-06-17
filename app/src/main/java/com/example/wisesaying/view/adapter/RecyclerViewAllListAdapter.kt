package com.example.wisesaying.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewSelfstoryHolderBinding
import com.example.wisesaying.db.entity.Pigme

class RecyclerViewAllListAdapter(var modelList:List<Pigme> = listOf()) :
    RecyclerView.Adapter<RecyclerViewAllListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_selfstory_holder, parent, false)
    )


    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding!!.apply {
            modelList[position].textStory.let {
               textViewRecyclerView.text = it
            }

            modelList[position].image.let {
                var uriStringValue = "android.resource://com.example.wisesaying/$it"
                if(it.length > 20){
                    uriStringValue = it
                }
                imageViewRecyclerView.setImageURI(Uri.parse(uriStringValue))
            }

        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewSelfstoryHolderBinding>(view)
    }

}




