package com.example.wisesaying

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object PremissonRequestDialog {
    fun show(
        context: Context,
        title: String = "",
        message: String = "",
        positiveText: String = "",
        onPositive: () -> Unit = {},
        negativeText: String = "",
        onNegative: () -> Unit = {}
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText, DialogInterface.OnClickListener { dialog, which ->
                onPositive()
            })
            .setNegativeButton(negativeText, DialogInterface.OnClickListener { dialog, which ->
                onNegative()
            })
            .create()
            .show()
    }
}