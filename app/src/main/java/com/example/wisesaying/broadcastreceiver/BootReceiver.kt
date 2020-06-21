package com.example.wisesaying.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wisesaying.preference.PrefRequestPremisson
import com.example.wisesaying.view.activity.MainActivity


//리시버는 이벤트를 받겠다고 하는것 안드로이드 시스템에게
// 하나의 이벤트만 사용해야함 안그러면 받지않을것.
// 등록하지 않은 이벤트는 받으면 안됨
class BootReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if(intent.action != Intent.ACTION_USER_PRESENT) return

        val intent = Intent(context, MainActivity::class.java)
        if (PrefRequestPremisson.getInstance(context).requestScore == 1) {
            // 한번에 두개 사용가능
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP )
            context.startActivity(intent)
        }
    }
}