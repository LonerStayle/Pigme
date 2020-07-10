package com.example.wisesaying.view.toast

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes


fun toastShort(context: Context?, @StringRes message:Int) {
    Toast.makeText(context, message ,Toast.LENGTH_SHORT).show()
}
fun toastShortTest(context: Context?,message:String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}