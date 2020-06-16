package com.example.wisesaying.preference

import android.content.Context
import android.net.Uri
import androidx.preference.PreferenceManager
import com.example.wisesaying.db.entity.Pigme
import org.json.JSONArray
import org.json.JSONException


class PrefSingleton private constructor(context: Context) {

    enum class Key(name: String) {
        SAVE_DATA("saveData"),
        SELF_STORY_USAGEMARK("selfstoryusagemark"),
        FRAME_LAYOUT_IMAGE_MODE_CHECK_VISIBILITY("frameLayoutImageModeCheckVisibility"),
        FREME_LAYOUT_SELFSTORY_USAGEMARKS_VISIBILITY("fremeLayoutSelfstoryUsagemarksVisibility"),
        RECYCLERVIEW_ADAPTER_CHANGE_SCORE("recyclerViewAadapterChangeScore"),
        CURRENT_VIEWPAGER("currentViewpager"),

        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_ISCHECKED("fragmentSettingSwitchWidgetSettingPremissonisChecked"),
        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_PREMISSON_TEXT("fragmentSettingSwitchWidgetSettingPremissonText"),
        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_ISCHECKED("fragmentsettingswitchwidgetsettingimagecontrolischecked"),
        FRAGMENT_SETTING_SWITCH_WIDGET_SETTING_IMAGE_CONTROL_TEXT("fragment_setting_switch_widget_setting_image_control_text")
      , MODELLIST_PREF_TEXT_STORY("modelListPreftextstory"),
        MODELLIST_PREF_IMAGE("modelListPrefimage"),
        MODELLIST_PREF_IMAGE_URI("modelListPrefimageuri"),

        MODELLIST_PREF_SELF_STORY_TEXT_STORY("modelListPrefselfstorytextstory"),
        MODELLIST_PREF_SELF_STORY_IMAGE("modelListPrefselfstoryimage"),
        MODELLIST_PREF_SELF_STORY_IMAGE_URI("modelListPrefselfstoryimageuri")


    }

    companion object {
        var instance: PrefSingleton? = null

        fun getInstance(context: Context): PrefSingleton = instance ?: PrefSingleton(context).also {
            instance = it
        }
    }

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    var requestScore: Int
        get() = pref.getInt(Key.SAVE_DATA.name, 0)
        set(value) {
            pref.edit().putInt(Key.SAVE_DATA.name, value).apply()
        }

    //ViewModel 안에 fun insert 함수를 사용흔적 저장
    var selfStoryUsageMark:Int
    get() = pref.getInt(Key.SELF_STORY_USAGEMARK.name,0)
    set(value){
        pref.edit().putInt(Key.SELF_STORY_USAGEMARK.name,value).apply()
    }



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

    // 전체리스트 (리싸이클러뷰) 명언 추가 여부에 따른 화면 변경
    var RecyclerViewAadapterChangeScore: Int
        get() = pref.getInt(Key.RECYCLERVIEW_ADAPTER_CHANGE_SCORE.name, 0)
        set(value) {
            pref.edit()
                .putInt(Key.RECYCLERVIEW_ADAPTER_CHANGE_SCORE.name, value).apply()
        }

    //ViewPager의 CurrentItem 위치 설정
    var currentViewpager: Int
        get() = pref.getInt(Key.CURRENT_VIEWPAGER.name, 0)
        set(value) {
            pref.edit()
                .putInt(Key.CURRENT_VIEWPAGER.name, value).apply()
        }

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
    var fragmentsettingSwitchwidgetSettingImageControlisChecked: Boolean
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
    var fragmentsettingSwitchwidgetSettingImageControlText: String
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

    var modelListPref: List<Pigme>
        get() {
            val jsonTextString = pref.getString(Key.MODELLIST_PREF_TEXT_STORY.name, null)
            val jsonImage = pref.getString(Key.MODELLIST_PREF_IMAGE.name, null)

            val urls = mutableListOf<Pigme>()
            if (jsonTextString != null) {
                try {
                    val textStory = JSONArray(jsonTextString)
                    val image = JSONArray(jsonImage)

                    for (i in 0 until textStory.length()) {
                        val urlStory = textStory.optString(i)
                        val urlImage = image.optString(i)


                        urls.add(i, Pigme(urlStory, urlImage))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return urls
        }
        set(value) {

            val textStory = JSONArray()
            val image = JSONArray()

            for (i in value.indices) {
                textStory.put(value[i].textStory)
                image.put(value[i].image)
            }
            if (value.isNotEmpty()) {
                pref.edit().putString(Key.MODELLIST_PREF_TEXT_STORY.name, textStory.toString())
                    .putString(Key.MODELLIST_PREF_IMAGE.name, image.toString())
                    .apply()
            } else {
                pref.edit().putString(Key.MODELLIST_PREF_TEXT_STORY.name, null)
                    .putString(Key.MODELLIST_PREF_IMAGE.name, null)
                    .apply()
            }
        }

    ///명언이 추가되었을시에 데이터 저장한 값 불러오기
    var modelListPrefSelfStory: List<Pigme>
        get() {
            val jsonTextString = pref.getString(Key.MODELLIST_PREF_SELF_STORY_TEXT_STORY.name, null)
            val jsonImage = pref.getString(Key.MODELLIST_PREF_SELF_STORY_IMAGE.name, null)

            val urls = mutableListOf<Pigme>()
            if (jsonTextString != null) {
                try {
                    val textStory = JSONArray(jsonTextString)
                    val image = JSONArray(jsonImage)



                    for (i in 0 until textStory.length()) {
                        val uriStory = textStory.optString(i)
                        val uriImage = image.optString(i)


                        urls.add(i, Pigme(uriStory,uriImage))
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return urls
        }
        set(value) {
            val textStory = JSONArray()
            val image = JSONArray()

            for (i in value.indices) {
                textStory.put(value[i].textStory)
                image.put(value[i].image)

            }
            if (value.isNotEmpty()) {
                pref.edit()
                    .putString(Key.MODELLIST_PREF_SELF_STORY_TEXT_STORY.name, textStory.toString())
                    .putString(Key.MODELLIST_PREF_SELF_STORY_IMAGE.name, image.toString())

                    .apply()
            } else {
                pref.edit().putString(Key.MODELLIST_PREF_SELF_STORY_TEXT_STORY.name, null)
                    .putString(Key.MODELLIST_PREF_SELF_STORY_IMAGE.name, null)
                    .apply()
            }
        }

}

