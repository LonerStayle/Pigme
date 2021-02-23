package kr.loner.pigme.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:image")
fun bindingAdapter(view: ImageView,url:String) = Glide.with(view.context).load(url).centerCrop().into(view)