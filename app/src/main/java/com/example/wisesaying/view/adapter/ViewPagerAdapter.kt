package com.example.wisesaying.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.ViewpagerBinding
import com.example.wisesaying.db.entity.Pigme

class ViewPagerAdapter(var modelList: List<Pigme> = listOf()) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewpager, parent, false)
        )

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.textViewStory?.text = modelList[position].textStory
        holder.binding?.imageView?.setImageResource(modelList[position].image)
        //        holder.bind(modelList[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ViewpagerBinding>(view)

    }
}

