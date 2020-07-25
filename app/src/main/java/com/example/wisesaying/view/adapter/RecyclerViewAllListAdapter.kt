package com.example.wisesaying.view.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewAlllistHolderBinding
import com.example.wisesaying.db.entity.Pigme
import com.example.wisesaying.preference.PrefUsageMark
import com.example.wisesaying.view.imageurl.imageUrl

class RecyclerViewAllListAdapter( var modelList:List<Pigme> = listOf(),var context: Context) :
    RecyclerView.Adapter<RecyclerViewAllListAdapter.ViewHolder>() {

    var saveListElement = mutableListOf<Int>()
    private val indexSelectedItems = SparseBooleanArray(0)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_alllist_holder, parent, false)
    )

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding!!.apply {

            layoutBackground.setBackgroundResource(holder.backgroundColor())
            modelList[position].image = imageUrl(modelList[position].image)
            pigme = modelList[position]
        }

        holder.itemView.setOnClickListener {
            if(indexSelectedItems[position,false]) {
                indexSelectedItems.put(position,false)
                holder.indexClickCancel()
            } else {
                indexSelectedItems.put(position,true)
                holder.indexIsClicked()
            }

        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<RecyclerviewAlllistHolderBinding>(view)

        fun indexIsClicked() {

            binding?.layoutBackground?.setBackgroundResource(R.color.allListIndexIsSelectColor)

            saveListElement.add(adapterPosition)

            PrefUsageMark.getInstance(context).deleteModelListOfIndex =
                saveListElement

        }
        fun indexClickCancel() {
            binding?.layoutBackground?.setBackgroundResource(R.color.allListIndexSelectCancelColor)

            saveListElement.remove(adapterPosition)

            PrefUsageMark.getInstance(context).deleteModelListOfIndex =
                saveListElement

        }
       fun backgroundColor(): Int {

            return if (indexSelectedItems.get(adapterPosition, false))
                R.color.allListIndexIsSelectColor
            else
                R.color.allListIndexSelectCancelColor
        }
    }
}




