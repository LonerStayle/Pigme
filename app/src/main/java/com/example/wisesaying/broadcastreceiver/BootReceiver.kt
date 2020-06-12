package com.example.wisesaying.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wisesaying.preference.PrefSingleton
import com.example.wisesaying.usagemarks.UsageMarksScore
import com.example.wisesaying.view.activity.MainActivity


class BootReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val intent = Intent(context, MainActivity::class.java)
        if (PrefSingleton.getInstance(context).requestScore == 1) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context.startActivity(intent)
        }
    }
}