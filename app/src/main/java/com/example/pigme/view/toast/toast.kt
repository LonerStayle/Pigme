package com.example.pigme.view.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.toastShort(@StringRes message:Int) {
    Toast.makeText(this, message ,Toast.LENGTH_SHORT).show()
}
fun Context.toastDebugTest(context: Context?,message:String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}