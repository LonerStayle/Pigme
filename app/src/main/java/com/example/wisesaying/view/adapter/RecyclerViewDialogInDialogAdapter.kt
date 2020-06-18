package com.example.wisesaying.view.adapter

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewThirdImagemodeSelectHolderBinding
import com.example.wisesaying.db.entity.Pigme
import com.example.wisesaying.preference.PrefSingleton

class RecyclerViewDialogInDialogAdapter(var modellist: List<Pigme> = listOf()) :
    RecyclerView.Adapter<RecyclerViewDialogInDialogAdapter.ImageItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_third_imagemode_select_holder, parent, false)
        )


    override fun getItemCount(): Int = modellist.size

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {

        holder.binding?.apply {
//            modellist[position].textStory.let {
//                textViewRecyclerViewInItemtext.text = it
//            }
//            modellist[position].image.let {
//                var uriStringValue = "android.resource://com.example.wisesaying/$it"
//                if (it.length > 20) {
//                    uriStringValue = it
//                }
//                imageViewRecyclerViewInItemImage.setImageURI(Uri.parse(uriStringValue))
//            }

            with(modellist[position]){
                textViewRecyclerViewInItemtext.text = textStory
                var uriStringValue = image
                if (image.length > 20) {
                    uriStringValue = image
                }
                imageViewRecyclerViewInItemImage.setImageURI(Uri.parse(uriStringValue))

            }

//리스너가 아니라 함수개념
            // 바꾸기
            holder.itemView.setOnClickListener {
                if (textViewBackgroundColorChecked.text == "#00B0FF") {
                    holder.clickListener(backgrounColorInsertLayout, textViewBackgroundColorChecked)
                } else {
                    holder.clickListener2(
                        backgrounColorInsertLayout,
                        textViewBackgroundColorChecked
                    )
                }
            }
        }

    }


    inner class ImageItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewThirdImagemodeSelectHolderBinding>(view)
        val clickListener =
            { layout: ConstraintLayout, textview: TextView ->
                layout.setBackgroundColor(Color.parseColor("#76FF03"))
                textview.text = "#76FF03"
            }
        val clickListener2 =
            { layout: ConstraintLayout, textview: TextView ->
                layout.setBackgroundColor(Color.parseColor("#00B0FF"))
                textview.text = "#00B0FF"
            }
    }

}