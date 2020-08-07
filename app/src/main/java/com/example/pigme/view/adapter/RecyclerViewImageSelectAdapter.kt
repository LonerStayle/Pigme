package com.example.pigme.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pigme.R
import com.example.pigme.databinding.RecyclerviewImageselectHolderBinding
import com.example.pigme.db.entity.GalleyImage
import com.example.pigme.view.imageurl.imageUrl


class RecyclerViewImageSelectAdapter(
    var imageSampleList: MutableList<GalleyImage> = mutableListOf(),
    val onClickEvent : (String?) -> Unit
) : RecyclerView.Adapter<RecyclerViewImageSelectAdapter.ImageSelectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSelectHolder =
        ImageSelectHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_imageselect_holder, parent, false)
        )

    override fun getItemCount(): Int = imageSampleList.size


    override fun onBindViewHolder(holder: ImageSelectHolder, position: Int) {

        holder.binding?.apply {
            imageSampleList[position].galleryImage.let {
                selectImageUrl = imageUrl(it)
            }

            holder.itemView.setOnClickListener {
              onClickEvent(imageSampleList[position].galleryImage)
            }
        }
    }


    inner class ImageSelectHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = DataBindingUtil.bind<RecyclerviewImageselectHolderBinding>(view)

    }
}
