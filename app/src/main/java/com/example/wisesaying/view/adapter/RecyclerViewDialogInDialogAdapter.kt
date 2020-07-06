package com.example.wisesaying.view.adapter

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val mSelectedItems = SparseBooleanArray(0)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_third_imagemode_select_holder, parent, false)
        )


    override fun getItemCount(): Int = modellist.size


    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {

        holder.binding?.apply {

            modellist[position].apply {
                backgrounColorInsertLayout.setBackgroundResource(holder.backgroundColorChanged())
                selfStory = textStory
                imageUri = imageUrl(image)
            }

            holder.itemView.setOnClickListener {

                if (mSelectedItems.get(position, false)) {
                    mSelectedItems.put(position, false)
                    holder.bindTheNotUseColorChange()
                } else {
                    mSelectedItems.put(position, true)
                    holder.bindTheUseColorChange()
                }

            }

        }
    }

    inner class ImageItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewThirdImagemodeSelectHolderBinding>(view)


        fun bindTheNotUseColorChange() {

            binding?.backgrounColorInsertLayout?.setBackgroundResource(R.color.dialogThirdSelectNotPressColor)

            saveDeleteListElement.remove(modellist.indexOf(modellist[adapterPosition]))

            PrefUsageMark.getInstance(context).deleteModelListOfIndex =
                saveDeleteListElement

        }

        fun bindTheUseColorChange() {
            binding?.backgrounColorInsertLayout?.setBackgroundResource(R.color.dialogThirdSelectPressColor)

            saveDeleteListElement.add(modellist.indexOf(modellist[adapterPosition]))

            PrefUsageMark.getInstance(context).deleteModelListOfIndex =
                saveDeleteListElement

        }

        fun backgroundColorChanged(): Int {

            return if (mSelectedItems.get(adapterPosition, false))
                R.color.dialogThirdSelectPressColor
            else
                R.color.dialogThirdSelectNotPressColor
        }
    }

    private fun imageUrl(image: String): String {
        var uriStringValue = "android.resource://com.example.wisesaying/$image"
        if (image.length > 20) {
            uriStringValue = image
        }
        return uriStringValue

    }

}