package com.example.wisesaying.preference

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager

object Preference_View {
    /**
     * 변수명 고치기 의미가 와닿게 변경 , 역활 분리 완료
     */

    // frameLayoutImageModeCheck.visibility_set 사진 출력모드 랜덤인지 고정인지를 나타내주는 visibility
    fun set_frameLayoutImageModeCheck_visibility(imageModeCheck_visibility: Int, context: Context) {
        val isvisibility_set = PreferenceManager.getDefaultSharedPreferences(context)
        val isvisibility_set_edit = isvisibility_set.edit()
        isvisibility_set_edit.putInt(
            "frameLayout_ImageModeCheck_visibility",
            imageModeCheck_visibility
        )
            .apply()
    }

    // frameLayoutImageModeCheck.visibility_get 사진 출력모드 랜덤인지 고정인지를 나타내주는 visibility
    fun get_frameLayoutImageModeCheck_visibility(context: Context): Int {
        val isvisibility_get = PreferenceManager.getDefaultSharedPreferences(context)
        val isvisibility_get_edit =
            isvisibility_get.getInt("frameLayout_ImageModeCheck_visibility", 0)
        return isvisibility_get_edit

    }

    // fremeLayout_selfstoty_Usagemark.visibility_set 유저가 새로운 명언을 추가했는지 안했는지를 나타내주는 visibility
    fun set_fremeLayout_selfstory_Usagemarks_visibility(context: Context, selfStoryUse: Int) {
        val prefSelfStoryUse_visibility_set = PreferenceManager.getDefaultSharedPreferences(context)
        val prefSelfStoryUse_visibility_set_edit = prefSelfStoryUse_visibility_set.edit()
        prefSelfStoryUse_visibility_set_edit.putInt(
            "fremeLayout_selfstory_Usagemarks_visibility",
            selfStoryUse
        )
            .apply()
    }

    // fremeLayout_selfstoty_Usagemark.visibility_get 유저가 새로운 명언을 추가했는지 안했는지를 나타내주는 visibility
    fun get_fremeLayout_selfstory_Usagemarks_visibility(context: Context): Int {
        val prefSelfStoryUse_visibility_get = PreferenceManager.getDefaultSharedPreferences(context)
        val prefSelfStoryUse_visibility_get_edit = prefSelfStoryUse_visibility_get.getInt(
            "fremeLayout_selfstory_Usagemarks_visibility",
            0x00000000
        )
        return prefSelfStoryUse_visibility_get_edit
    }

    fun set_RecyclerViewAadapterChangeScore(AadapterScore: Int, context: Context) {
        val recyclerViewArrayChange_set = PreferenceManager.getDefaultSharedPreferences(context)
        val recyclerViewArrayChange_set_edit = recyclerViewArrayChange_set.edit()
        recyclerViewArrayChange_set_edit.putInt("RecyclerViewAadapterChange", AadapterScore)
            .apply()
    }

    fun get_RecyclerViewAadapterChangeScore(context: Context): Int {
        val recyclerViewArrayChange_get = PreferenceManager.getDefaultSharedPreferences(context)
        val recyclerViewArrayChange_get_edit =
            recyclerViewArrayChange_get.getInt("RecyclerViewAadapterChange", 0)
        return recyclerViewArrayChange_get_edit
    }

    fun set_CurrentViewpager(viewPager: Int, context: Context) {
        val currentViewpager_set = PreferenceManager.getDefaultSharedPreferences(context)
        val currentViewpager_set_edit = currentViewpager_set.edit()
        currentViewpager_set_edit.putInt("viewpagerCurrentItem", viewPager)
            .apply()
    }

    fun get_CurrentViewpager(context: Context): Int {
        val currentViewpager_get = PreferenceManager.getDefaultSharedPreferences(context)
        val currentViewpager_get_edit = currentViewpager_get.getInt("viewpagerCurrentItem", 0)
        return currentViewpager_get_edit

    }


    //FragmentSetting_switchWidgetSettingPremisson.isChecked_set 스위치 버튼의 권한 요청에 따른 true/false표시 변환
    fun set_FragmentSetting_switchWidgetSettingPremissonisChecked(
        boolean: Boolean, context: Context
    ) {
        val isCheckedPref_set = PreferenceManager.getDefaultSharedPreferences(context)
        val isCheckedPref_set_edit = isCheckedPref_set.edit()
        isCheckedPref_set_edit.putBoolean(
            "FragmentSetting_switchWidgetSettingPremissonisChecked",
            boolean
        )
//        edit1.putString("saveData2", text)
            .apply()
    }


    //FragmentSetting_switchWidgetSettingPremisson.isChecked_get 스위치 버튼의 권한 요청에 따른 true/false 표시 변환
    fun get_FragmentSetting_switchWidgetSettingPremissonisChecked(context: Context): Boolean {
        val isCheckedPref_get = PreferenceManager.getDefaultSharedPreferences(context)
        val isCheckedPref_get_edit = isCheckedPref_get.getBoolean(
            "FragmentSetting_switchWidgetSettingPremissonisChecked",
            false
        )
//        val new1 = loadData1.getString("saveData2", "OFF\t")
        return isCheckedPref_get_edit

    }

    //FragmentSetting_switchWidgetSettingPremisson.text _ set 스위치 버튼의 권한 요청에 따른 text 표시 변환
    fun set_FragmentSetting_switchWidgetSettingPremissonisText(
        text: String, context: Context
    ) {
        val istextPref_set = PreferenceManager.getDefaultSharedPreferences(context)
        val istextedPref_set_edit = istextPref_set.edit()
            .putString("FragmentSetting_switchWidgetSettingPremissonisText", text)
            .apply()
    }


    //FragmentSetting_switchWidgetSettingPremisson.text_ get 스위치 버튼의 권한 요청에 따른 text 표시 변환
    fun get_FragmentSetting_switchWidgetSettingPremissonisText(context: Context): String {
        val istextPref_set = PreferenceManager.getDefaultSharedPreferences(context)
        val istextPref_set_edit =
            istextPref_set.getString("FragmentSetting_switchWidgetSettingPremissonisText", "OFF\t")
        return istextPref_set_edit!!
    }


    //FragmentSetting_switchWidgetSettingimageControl.isChecked_set 스위치 버튼 ture/false 표시 변화
    fun set_FragmentSetting_switchWidgetSettingimageControlisChecked(
        boolean: Boolean,
        context: Context
    ) {
        val isCheckedPref_set =
            PreferenceManager.getDefaultSharedPreferences(context)
        val isCheckedPref_set_edit = isCheckedPref_set.edit()
        isCheckedPref_set_edit.putBoolean(
            "FragmentSetting_switchWidgetSettingimageControlisChecked",
            boolean
        )
            .apply()
    }


    //FragmentSetting_switchWidgetSettingimageControl.isChecked_set 스위치 버튼 ture/false 표시 변화
    fun get_FragmentSetting_switchWidgetSettingimageControlisChecked(context: Context): Boolean {
        val isCheckedPref_get = PreferenceManager.getDefaultSharedPreferences(context)
        val isCheckedPref_get_edit = isCheckedPref_get.getBoolean(
            "FragmentSetting_switchWidgetSettingimageControlisChecked",
            false
        )
        return isCheckedPref_get_edit
    }

    //FragmentSetting_switchWidgetSettingimageControl.isChecked_set 스위치 버튼 ture/false 표시 변화
    fun set_FragmentSetting_switchWidgetSettingimageControlisText(
        textOnoff: String,
        context: Context
    ) {
        val isTextPref_set =
            PreferenceManager.getDefaultSharedPreferences(context)
        val isTextPref_set_edit = isTextPref_set.edit()
        isTextPref_set_edit.putString(
            "FragmentSetting_switchWidgetSettingimageControlisText",
            textOnoff
        )
            .apply()
    }

    //FragmentSetting_switchWidgetSettingimageControl.isChecked_set 스위치 버튼 ture/false 표시 변화
    fun get_FragmentSetting_switchWidgetSettingimageControlisText(context: Context): String {
        val isTextPref_get = PreferenceManager.getDefaultSharedPreferences(context)
        val isTextPref_get_edit = isTextPref_get.getString(
            "FragmentSetting_switchWidgetSettingimageControlisText",
            "OFF\t"
        )
        return isTextPref_get_edit!!
    }


}