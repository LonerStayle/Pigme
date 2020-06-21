package com.example.wisesaying.preference

import android.content.Context
import androidx.preference.PreferenceManager

class PrefAllListAdapter private constructor(context: Context){
    enum class Key {
        RECYCLERVIEW_ADAPTER_CHANGE_SCORE
    }

    companion object {

        var instance: PrefAllListAdapter? = null

        fun getInstance(context: Context): PrefAllListAdapter = instance ?: PrefAllListAdapter(context).also {
            instance = it
        }
    }

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    // 전체리스트 (리싸이클러뷰) 명언 추가 여부에 따른 화면 변경
    var recyclerViewAdapterChangeScore: Int
        get() = pref.getInt(Key.RECYCLERVIEW_ADAPTER_CHANGE_SCORE.name, 0)
        set(value) {
            pref.edit()
                .putInt(Key.RECYCLERVIEW_ADAPTER_CHANGE_SCORE.name, value).apply()
        }

}