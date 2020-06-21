package com.example.wisesaying.preference

import android.content.Context
import androidx.preference.PreferenceManager

class PrefUsageMark private constructor(context: Context) {

    enum class Key {
        SELF_STORY_USAGEMARK,
        LIVE_DATA_FIRST_USETRACE
    }

    companion object {
        var instance: PrefUsageMark? = null

        fun getInstance(context: Context): PrefUsageMark = instance ?: PrefUsageMark(context).also {
            instance = it
        }
    }

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    //ViewModel 안에 fun insert 함수를 사용흔적 저장
    var selfStoryUsageMark: Int
        get() = pref.getInt(Key.SELF_STORY_USAGEMARK.name, 0)
        set(value) {
            pref.edit().putInt(Key.SELF_STORY_USAGEMARK.name, value).apply()
        }

    var liveDataFirstUseTrace:Boolean
    get() = pref.getBoolean(Key.LIVE_DATA_FIRST_USETRACE.name,true)
    set(value) {
        pref.edit().putBoolean(Key.LIVE_DATA_FIRST_USETRACE.name,value).apply()
    }

}