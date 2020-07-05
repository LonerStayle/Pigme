package com.example.wisesaying.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object DialogSimple {
    fun show(
        context: Context,
        dialogtitle: Int,
        dialogmessage: Int ,
        positiveText: String ,
        onPositive: () -> Unit = {},
        negativeText: String = "",
        onNegative: () -> Unit = {}
    ) =  AlertDialog.Builder(context)
            .setTitle(dialogtitle)
            .setMessage(dialogmessage)
            .setPositiveButton(positiveText) { _, _ ->
                onPositive()
            }
        .setNegativeButton(negativeText) { _, _ ->

            onNegative()
        }
        .create()
            .show()
    }
