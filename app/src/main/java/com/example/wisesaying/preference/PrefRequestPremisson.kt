package com.example.wisesaying.preference

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.wisesaying.db.entity.Pigme
import org.json.JSONArray
import org.json.JSONException


class PrefRequestPremisson private constructor(context: Context) {
// 네임은 _ 가 생김 val key 따로 만들것
    //내부에 네임이 있어서 심지어 키값 없애도 자공함
    enum class Key {
        SAVE_DATA,

    }

    companion object {
        var instance: PrefRequestPremisson? = null

        fun getInstance(context: Context): PrefRequestPremisson = instance ?: PrefRequestPremisson(context).also {
            instance = it
        }
    }

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    var requestScore: Int
        get() = pref.getInt(Key.SAVE_DATA.name, 0)
        set(value) {
            pref.edit().putInt(Key.SAVE_DATA.name, value).apply()
        }

}

