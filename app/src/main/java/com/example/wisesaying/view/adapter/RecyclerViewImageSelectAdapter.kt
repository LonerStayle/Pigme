package com.example.wisesaying.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisesaying.R
import com.example.wisesaying.databinding.RecyclerviewImageselectHolderBinding

class RecyclerViewImageSelectAdapter(var imageSampleList: List<Int> = listOf(),
recyclerviewImageSelectClcikevent: RecyclerviewImageSelectClcikEvent) :
    RecyclerView.Adapter<RecyclerViewImageSelectAdapter.ImageSelectHolder>() {

    private var imageSelectClickevent :RecyclerviewImageSelectClcikEvent? = null

    // 생성자
    init {
        this.imageSelectClickevent = recyclerviewImageSelectClcikevent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSelectHolder =
        ImageSelectHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_imageselect_holder, parent, false), this.imageSelectClickevent!!
        )


    override fun getItemCount(): Int = imageSampleList.size

    /**
     * TODO(클릭이벤트 넣을것임, 갤러리 내에서 사진찾기 추가할 것  )
     */
    override fun onBindViewHolder(holder: ImageSelectHolder, position: Int) {

        holder.binding?.apply {
            imageViewSampleimage.setImageResource(imageSampleList[position])
        }

    }

    inner class ImageSelectHolder(
        view: View,
        recyclerviewImageSelectClcikevent: RecyclerviewImageSelectClcikEvent
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val binding = DataBindingUtil.bind<RecyclerviewImageselectHolderBinding>(view)
        var this_ImageSelectClickevent: RecyclerviewImageSelectClcikEvent? = null

        init {
            view.setOnClickListener(this)
            this_ImageSelectClickevent = recyclerviewImageSelectClcikevent
        }

        override fun onClick(v: View?) {
           this.this_ImageSelectClickevent?.onclickEvent(imageSampleList[adapterPosition])

        }
    }
}

/**
 * 뷰홀더 안에서 셋온 클릭리스너 하는법
inner class ImageSelectHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
val binding = DataBindingUtil.bind<RecyclerviewImageselectHolderBinding>(view)

init {
view.setOnClickListener {
val pos = adapterPosition
if(pos != RecyclerView.NO_POSITION){

}
}
}


}
 */