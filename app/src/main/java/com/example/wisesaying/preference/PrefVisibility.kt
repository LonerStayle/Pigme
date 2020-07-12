package com.example.wisesaying.preference

import android.content.Context
import androidx.preference.PreferenceManager

class PrefVisibility private constructor(context:Context){

    enum class Key{

        TEXTVIEW_IMAGE_MODE_CHECK_VISIBILITY,
    }
    companion object {
        var instance: PrefVisibility? = null

        fun getInstance(context: Context): PrefVisibility = instance ?: PrefVisibility(context).also {
            instance = it
        }
    }

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    // frameLayoutImageModeCheck.visibility 사진 출력모드 랜덤인지 고정인지를 나타내주는 visibility
    var textViewImageModeCheckVisibility: Int
        get() = pref.getInt(Key.TEXTVIEW_IMAGE_MODE_CHECK_VISIBILITY.name, 0)
        set(value) {
            pref.edit()
                .putInt(Key.TEXTVIEW_IMAGE_MODE_CHECK_VISIBILITY.name, value).apply()
        }

}