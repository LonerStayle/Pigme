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
import com.example.wisesaying.view.adapter.imageurl.imageUrl

class RecyclerViewAllListAdapter(private var modelList:List<Pigme> = listOf()) :
    RecyclerView.Adapter<RecyclerViewAllListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_alllist_holder, parent, false)
    )

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding!!.apply {

            modelList[position].image = imageUrl(modelList[position].image)
            pigme = modelList[position]
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewAlllistHolderBinding>(view)
    }

}




