package com.example.wisesaying.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewAlllistHolderBinding
import com.example.wisesaying.db.entity.Pigme

class RecyclerViewAllListAdapter(private var modelList:List<Pigme> = listOf()) :
    RecyclerView.Adapter<RecyclerViewAllListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_alllist_holder, parent, false)
    )


    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding!!.apply {

            var uriStringValue= "android.resource://com.example.wisesaying/${modelList[position].image}"
            if (modelList[position].image.length > 20) {
                uriStringValue = modelList[position].image
            }
            modelList[position].image = uriStringValue

            pigme = modelList[position]
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewAlllistHolderBinding>(view)
    }

}




