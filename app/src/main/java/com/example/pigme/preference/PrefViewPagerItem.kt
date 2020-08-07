package com.example.pigme.preference

import android.content.Context
import androidx.preference.PreferenceManager

class PrefViewPagerItem private constructor(context: Context){

    enum class Key {
        CURRENT_VIEWPAGER
    }

    companion object {
        var instance: PrefViewPagerItem? = null

        fun getInstance(context: Context): PrefViewPagerItem = instance ?: PrefViewPagerItem(context).also {
            instance = it
        }
    }
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    //ViewPager의 CurrentItem 위치 설정
    var currentViewpager: Int
        get() = pref.getInt(Key.CURRENT_VIEWPAGER.name, 0)
        set(value) {
            pref.edit()
                .putInt(Key.CURRENT_VIEWPAGER.name, value).apply()
        }
}