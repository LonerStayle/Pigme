package com.example.wisesaying.view.adapter

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewThirdImagemodeSelectHolderBinding
import com.example.wisesaying.db.entity.Pigme
import kotlinx.android.synthetic.main.dialog_image_select_mode_recycler_view_.*

class RecyclerViewDialogInAdapter(var modellist: List<Pigme> = listOf()) :
    RecyclerView.Adapter<RecyclerViewDialogInAdapter.ImageItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_third_imagemode_select_holder, parent, false)
        )


    override fun getItemCount(): Int = modellist.size

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {

        holder.binding?.apply {
            modellist[position].image.let {
                var uriStringValue = "android.resource://com.example.wisesaying/$it"
                if (it.length > 20) {
                    uriStringValue = it
                }
                imageView2.setImageURI(Uri.parse(uriStringValue))
            }
            textView.text = modellist[position].textStory


            holder.itemView.setOnClickListener {
                if(textViewBackgroundColorChecked.text=="#00B0FF") {
                    holder.clickListener(constLayout,textViewBackgroundColorChecked)
                }else {
                    holder.clickListener2(constLayout,textViewBackgroundColorChecked)
                }
            }
        }

    }


    inner class ImageItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewThirdImagemodeSelectHolderBinding>(view)
        val clickListener =
            { layout: ConstraintLayout, textview: TextView -> layout.setBackgroundColor(Color.parseColor("#76FF03"))
            textview.text ="#76FF03"}
        val clickListener2 =
            { layout: ConstraintLayout, textview: TextView -> layout.setBackgroundColor(Color.parseColor("#00B0FF"))
                textview.text ="#00B0FF"}
    }

}