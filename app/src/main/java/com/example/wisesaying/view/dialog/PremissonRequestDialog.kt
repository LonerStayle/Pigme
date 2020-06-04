package com.example.wisesaying.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

/**
 * 아직 사용 못해봄..
 */
object PremissonRequestDialog {
    fun show(
        context: Context,
        frestdialogtitle: String = "",
        frestdialogmessage: String = "",
        positiveText: String = "",
        onPositive: () -> Unit = {},
        negativeText: String = "",
        onNegative: () -> Unit = {}
    ) {
       val alertDialog =  AlertDialog.Builder(context)
               alertDialog
            .setTitle(frestdialogtitle)
            .setMessage(frestdialogmessage)
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