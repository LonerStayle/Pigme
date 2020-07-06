package com.example.wisesaying.view.toast

import android.content.Context
import android.widget.Toast

fun toastShort(context: Context?,message:Int) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}