package com.example.wisesaying.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

/**
 * 아직 사용 못해봄..
 *
 * FIXME: 굳이 변수명 사용 필요가없음 .
 */
object DialogSimple {
    fun show(
        context: Context,
        dialogtitle: String,
        dialogmessage: String ,
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
