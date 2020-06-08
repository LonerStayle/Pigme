package com.example.wisesaying.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.FragmentSelfStoryImageSelectBinding
import com.example.wisesaying.databinding.RecyclerviewImageselectHolderBinding

class RecyclerView_ImageSelectAdapter(var imageSampleList: List<Int> = listOf()) :
    RecyclerView.Adapter<RecyclerView_ImageSelectAdapter.ImageSelectHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSelectHolder {

        return ImageSelectHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_imageselect_holder, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return imageSampleList.size
    }

    /**
     * TODO(클릭이벤트 넣을것임, 갤러리 내에서 사진찾기 추가할 것  )
     */
    override fun onBindViewHolder(holder: ImageSelectHolder, position: Int) {

        holder.binding?.apply {
            imageViewSampleimage.setImageResource(imageSampleList[position])
        }
        holder.image_setOnClickListener?.setOnClickListener {
//        오류    holder.fragmentSelfStoryImageSelect_ImageView?.setImageResource(imageSampleList[holder.adapterPosition])
        }

    }

    inner class ImageSelectHolder(view: View) : RecyclerView.ViewHolder(view) {
       val binding = DataBindingUtil.bind<RecyclerviewImageselectHolderBinding>(view)
        val image_setOnClickListener = DataBindingUtil.bind<RecyclerviewImageselectHolderBinding>(view)?.imageViewSampleimage
//      오류  val fragmentSelfStoryImageSelect_ImageView = DataBindingUtil.bind<FragmentSelfStoryImageSelectBinding>(view)?.imageViewBackgroundImage
    }
}