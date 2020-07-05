package com.example.wisesaying.view.adapter

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewThirdImagemodeSelectHolderBinding
import com.example.wisesaying.db.entity.Pigme
import com.example.wisesaying.preference.PrefUsageMark


class RecyclerViewDialogInDialogAdapter(
    var modellist: List<Pigme> = listOf(),
    var context: Context
) :
    RecyclerView.Adapter<RecyclerViewDialogInDialogAdapter.ImageItemViewHolder>() {
    private val saveDeleteListElement = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_third_imagemode_select_holder, parent, false)
        )


    override fun getItemCount(): Int = modellist.size


    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {

        holder.binding?.apply {
//            with(modellist[position]) {
//                textViewRecyclerViewInItemtext.text = textStory
//                var uriStringValue = image
//                if (image.length > 20) {
//                    uriStringValue = image
//                }
//                imageViewRecyclerViewInItemImage.setImageURI(Uri.parse(uriStringValue))
//            }
            modellist[position].apply {
                textViewRecyclerViewInItemtext.text = textStory
                var uriStringValue = "android.resource://com.example.wisesaying/$image"
                if (image.length > 20) {
                    uriStringValue = image
                }
                imageUri = uriStringValue
            }


            holder.itemView.setOnClickListener {


                if (textViewBackgroundColorChecked.text == "#76FF03") {
                    backgrounColorInsertLayout.setBackgroundColor(Color.parseColor("#00B0FF"))
                    textViewBackgroundColorChecked.text = "#00B0FF"


                    saveDeleteListElement.remove(modellist.indexOf(modellist[position]))

                    PrefUsageMark.getInstance(context).deleteModelListOfIndex =
                        saveDeleteListElement


                } else {
                    backgrounColorInsertLayout.setBackgroundColor(Color.parseColor("#76FF03"))
                    textViewBackgroundColorChecked.text = "#76FF03"

                    saveDeleteListElement.add(modellist.indexOf(modellist[position]))

                    PrefUsageMark.getInstance(context).deleteModelListOfIndex =
                        saveDeleteListElement

                }
                holder.itemViewType
            }

        }
    }

    open inner class ImageItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewThirdImagemodeSelectHolderBinding>(view)

    }

}