package com.example.pigme.view.dialog

import android.app.AlertDialog
import android.content.Context

object DialogSimple {
    fun show(
        context: Context,
        dialogTitle: Int,
        dialogMessage: Int,
        positiveText: Int,
        onPositive: () -> Unit = {},
        negativeText: Int,
        onNegative: (() -> Unit) = {}
    ) =  AlertDialog.Builder(context)
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveButton(positiveText) { _, _ ->
                onPositive()
            }
        .setNegativeButton(negativeText) { _, _ ->

            onNegative()
        }
        .create()
            .show()
    }
