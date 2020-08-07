package com.example.pigme.view.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun toastShort(context: Context?, @StringRes message:Int) {
    Toast.makeText(context, message ,Toast.LENGTH_SHORT).show()
}
fun toastShortTest(context: Context?,message:String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}