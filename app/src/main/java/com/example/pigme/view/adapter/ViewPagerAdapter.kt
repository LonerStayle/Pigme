package com.example.pigme.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pigme.R
import com.example.pigme.databinding.ViewpagerBinding
import com.example.pigme.db.entity.Pigme
import com.example.pigme.view.imageurl.imageUrl

class ViewPagerAdapter(var modelList: List<Pigme> = listOf()) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewpager, parent, false)
        )

    override fun getItemCount(): Int = modelList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding?.apply {
            modelList[position].textStory.let { textStory = it }
            modelList[position].image.let {
                url = imageUrl(it)
            }
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ViewpagerBinding>(view)

    }


}



