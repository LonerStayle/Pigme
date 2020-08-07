package com.example.pigme.preference

import android.content.Context
import androidx.preference.PreferenceManager

class PrefFragmentSetting private constructor(context: Context){

    enum class Key{
        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_ISCHECKED,
        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_TEXT,
        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_ISCHECKED,
        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_TEXT
    }

    companion object {
        var instance: PrefFragmentSetting? = null

        fun getInstance(context: Context): PrefFragmentSetting = instance ?: PrefFragmentSetting(context).also {
            instance = it
        }
    }
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    //FragmentSetting_switchWidgetSettingPremisson.isChecked 스위치 버튼의 권한 요청에 따른 true/false표시 변환
    var fragmentSettingSwitchWidgetSettingPremissonisChecked: Boolean
        get() = pref.getBoolean(
            Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_ISCHECKED.name,
            false
        )
        set(value) {
            pref.edit().putBoolean(
                Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_ISCHECKED.name,
                value
            ).apply()
        }

    //FragmentSetting_switchWidgetSettingPremisson.text 스위치 버튼의 권한 요청에 따른 text 표시 변환
    var fragmentSettingSwitchWidgetSettingPremissonText: String
        get() = pref.getString(
            Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_TEXT.name,
            "OFF\t")!!
        set(value) {
            pref.edit()
                .putString(Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_TEXT.name, value)
                .apply()
        }

    //FragmentSetting_switchWidgetSettingimageControl.isChecked 스위치 버튼 ture/false 표시 변화
    var fragmentSettingSwitchWidgetSettingImageControlisChecked: Boolean
        get() = pref.getBoolean(
            Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_ISCHECKED.name,
            false
        )
        set(value) {
            pref.edit().putBoolean(
                Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_ISCHECKED.name,
                value
            ).apply()
        }

    //FragmentSetting_switchWidgetSettingimageControl.text 스위치 버튼에 따른 표시 변화
    var fragmentSettingSwitchWidgetSettingImageControlText: String
        get() = pref.getString(
            Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_TEXT.name,
            "OFF\t"
        )!!
        set(value) {
            pref.edit().putString(
                Key.FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_TEXT.name,
                value
            ).apply()
        }


}