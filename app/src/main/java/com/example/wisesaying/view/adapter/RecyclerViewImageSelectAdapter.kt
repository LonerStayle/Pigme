package com.example.wisesaying.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewImageselectHolderBinding
import com.example.wisesaying.db.entity.GalleyImage
import com.example.wisesaying.view.adapter.imageurl.imageUrl


class RecyclerViewImageSelectAdapter(
    var imageSampleList: MutableList<GalleyImage> = mutableListOf(),
    var imageViewbacgroundImage: ImageView,
    var textViewGalleryGuide:TextView,
    var textViewImageBackgroundResIdCheck:TextView
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
               holder.clickFunction()
            }
        }
    }


    inner class ImageSelectHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = DataBindingUtil.bind<RecyclerviewImageselectHolderBinding>(view)

        //함수로 바꿔써도됨 리스너 개념은 아님
        fun clickFunction() {
            binding?.apply {
                val image = imageSampleList[adapterPosition].galleryImage


                Glide.with(imageViewbacgroundImage.context).load(imageUrl(image)).into(imageViewbacgroundImage)

                  textViewImageBackgroundResIdCheck.text = imageUrl(image)
                textViewGalleryGuide.clearAnimation()
                textViewGalleryGuide.visibility = View.GONE
            }
        }
    }
}
