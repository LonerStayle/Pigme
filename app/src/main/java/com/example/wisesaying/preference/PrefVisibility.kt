package com.example.wisesaying.preference

import android.content.Context
import androidx.preference.PreferenceManager

class PrefVisibility private constructor(context:Context){

    enum class Key{

        FRAME_LAYOUT_IMAGE_MODE_CHECK_VISIBILITY,
        FREME_LAYOUT_SELFSTORY_USAGEMARKS_VISIBILITY
    }
    companion object {
        var instance: PrefVisibility? = null

        fun getInstance(context: Context): PrefVisibility = instance ?: PrefVisibility(context).also {
            instance = it
        }
    }

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    // frameLayoutImageModeCheck.visibility 사진 출력모드 랜덤인지 고정인지를 나타내주는 visibility
    var frameLayoutImageModeCheckVisibility: Int
        get() = pref.getInt(Key.FRAME_LAYOUT_IMAGE_MODE_CHECK_VISIBILITY.name, 0)
        set(value) {
            pref.edit()
                .putInt(Key.FRAME_LAYOUT_IMAGE_MODE_CHECK_VISIBILITY.name, value).apply()
        }

    // fremeLayout_selfstoty_Usagemark.visibility 유저가 새로운 명언을 추가했는지 안했는지를 나타내주는 visibility
    var fremeLayoutSelfstoryUsagemarksVisibility: Int
        get() = pref.getInt(
            Key.FREME_LAYOUT_SELFSTORY_USAGEMARKS_VISIBILITY.name,
            0
        )
        set(value) {
            pref.edit()
                .putInt(Key.FREME_LAYOUT_SELFSTORY_USAGEMARKS_VISIBILITY.name, value).apply()
        }

}