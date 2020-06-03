package com.example.wisesaying

import android.content.Context
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import com.example.wisesaying.db.entity.Pigme

import kotlinx.android.synthetic.main.fragment_settings.view.*
import org.json.JSONArray
import org.json.JSONException

/**
 * TODO: object 화 시켜서 쓰기, 굳이 객체화 시킬 필요성이 없음
 */
class Preference {
    /**
     *FIXME: 권한 관련 preference 분리
     */

//    var permission:Int
//    get() {}
//    set(value) {}


    //권한 요청 상태 불러오기
    fun get_Permission(context: Context): Int {
        val loadData = PreferenceManager.getDefaultSharedPreferences(context)
        val new = loadData.getInt("saveData", 0)
        return new
    }

    //군한 요청 저장
    fun set_Permission(score: Int, context: Context) {
        val saveNumber =
            PreferenceManager.getDefaultSharedPreferences(context)
        val edit = saveNumber.edit()
        edit.putInt("saveData", score)
            .apply()
    }

    /**
     * FIXME: 변수명 고치기 의미가 와닿게
     */

    //스위치의 권한 요청 상태 불러오기
    fun get_FragmentSetting_switchWidgetSettingPremisson(root: View, context: Context): Int {
        val loadData1 = PreferenceManager.getDefaultSharedPreferences(context)
        val new = loadData1.getBoolean("saveData1", false)
        val new1 = loadData1.getString("saveData2", "OFF\t")
        val new2 = loadData1.getInt("saveData3", 0)
        root.switchWidget_Setting_Premisson.isChecked = new
        root.switchWidget_Setting_Premisson.text = new1
        return new2


    }

    //스위치의 권한 요청 저장
    fun set_FragmentSetting_switchWidgetSettingPremisson(
        boolean: Boolean,
        text: String,
        score: Int,
        context: Context
    ) {
        val saveNumber2 =
            PreferenceManager.getDefaultSharedPreferences(context)
        val edit1 = saveNumber2.edit()
        edit1.putBoolean("saveData1", boolean)
        edit1.putString("saveData2", text)

            .apply()
    }


    // 앱 사진 고정기능 로드 데이터
    /**
     * FiXME: root가 문제, 책임도 너무 여러개임 , 리턴을 해서 해당 뷰에게 값을 주는형식이 가벼움 ,역활도 분리하기
     */
    fun get_FragmentSetting_switchWidgetSettingimageControl(root: View, context: Context) {
        val loadData2 = PreferenceManager.getDefaultSharedPreferences(context)
        val new = loadData2.getBoolean("saveData2-1", false)
        val new1 = loadData2.getString("saveData2-2", "OFF\t")

        root.switchWidget_Setting_imageControl.isChecked = new
        root.switchWidget_Setting_imageControl.text = new1
    }

    //앱 사진 고정기능 세이브 데이터
    fun set_FragmentSetting_switchWidgetSettingimageControl(
        boolean: Boolean,
        textOnOff: String,
        changeClickCount: Int,
        context: Context
    ) {
        val saveNumber2 =
            PreferenceManager.getDefaultSharedPreferences(context)
        val edit1 = saveNumber2.edit()
        edit1.putBoolean("saveData2-1", boolean)
        edit1.putString("saveData2-2", textOnOff)
        edit1.putInt("saveData2-3", changeClickCount)
            .apply()
    }

    /**
     * FIXME: preference 뷰 관련
     */
    // 어댑터 변경 참고용 frameLayout 의 visibility 설정 get
    fun get_frameLayoutImageModeCheck(context: Context): Int {
        val lodaData_ModelListCount = PreferenceManager.getDefaultSharedPreferences(context)
        val modelListvisibility = lodaData_ModelListCount.getInt("frameLayout_ImageModeCheck", 0)
        return modelListvisibility


    }

    // 어댑터 변경 참고용 frameLayout 의 visibility 설정 set
    fun set_frameLayoutImageModeCheck(imageModeCheck_visibility: Int, context: Context) {
        val saveData_visibility = PreferenceManager.getDefaultSharedPreferences(context)
        val save_visibility = saveData_visibility.edit()
        save_visibility.putInt("frameLayout_ImageModeCheck", imageModeCheck_visibility)
            .apply()
    }

    //어댑터 모델 리스트 set
    fun set_ModelListPref(context: Context, newModel: List<Pigme>) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val textStory = JSONArray()
        val image = JSONArray()
        for (i in newModel.indices) {
            textStory.put(newModel[i].textStory)
            image.put(newModel[i].image)
        }
        if (newModel.isNotEmpty()) {
            editor.putString("key2", textStory.toString())
            editor.putString("key", image.toString())
        } else {
            editor.putString("key2", null)
            editor.putString("key", null)
        }
        editor.apply()
    }

    //어댑터 모델리스트  get
    fun get_ModelListPref(context: Context): List<Pigme> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val json = prefs.getString("key2", null)
        val json2 = prefs.getString("key", null)
        val urls = mutableListOf<Pigme>()
        if (json != null) {
            try {
                val textStory = JSONArray(json)
                val image = JSONArray(json2)

                for (i in 0 until textStory.length()) {
                    val url = textStory.optString(i)
                    val url2 = image.optString(i).toInt()
                    urls.add(
                        i,
                        Pigme(url, url2)
                    )
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return urls
    }

    //명언이 추가되었을시에 어댑터 뷰모델 set
    fun set_ModelListPrefSelfStory(context: Context, newModel: List<Pigme>) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val textStory = JSONArray()
        val image = JSONArray()
        for (i in newModel.indices) {
            textStory.put(newModel[i].textStory)
            image.put(newModel[i].image)
        }
        if (newModel.isNotEmpty()) {
            editor.putString("Selfkey2", textStory.toString())
            editor.putString("Selfkey", image.toString())
        } else {
            editor.putString("Selfkey2", null)
            editor.putString("Selfkey", null)
        }
        editor.apply()
    }

    //명언이 추가되었을 시에 어댑터 뷰모델  get
    fun get_ModelListPrefSelfStory(context: Context): List<Pigme> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val json = prefs.getString("Selfkey2", null)
        val json2 = prefs.getString("Selfkey", null)
        val urls = mutableListOf<Pigme>()
        if (json != null) {
            try {
                val textStory = JSONArray(json)
                val image = JSONArray(json2)

                for (i in 0 until textStory.length()) {
                    val url = textStory.optString(i)
                    val url2 = image.optString(i).toInt()
                    urls.add(
                        i,
                        Pigme(url, url2)
                    )
                }


            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return urls
    }


    fun set_CurrentViewpager(viewPager: Int, context: Context) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putInt("viewpagerCurrentItem", viewPager)
            .apply()
        Log.d("DataModel", "save : ${viewPager}")
    }

    fun get_CurrentViewpager(context: Context): Int {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val prefLoad2 = pref.getInt("viewpagerCurrentItem", 0)
        Log.d("DataModel", "last saved : $prefLoad2")
        return prefLoad2

    }


    fun set_fremeLayout_selfstoty_Usagemarks(context: Context, selfStoryUse: Int) {
        val prefSelfStoryUseset = PreferenceManager.getDefaultSharedPreferences(context)
        val editorSelfStoryUse = prefSelfStoryUseset.edit()
        editorSelfStoryUse.putInt("SelfStory", selfStoryUse)
            .apply()
    }

    fun get_fremeLayout_selfstoty_Usagemarks(context: Context): Int {
        val prefSelfStoryUseGet = PreferenceManager.getDefaultSharedPreferences(context)
        val prefSelfuse = prefSelfStoryUseGet.getInt("SelfStory", 0x00000000)
        return prefSelfuse
    }

    fun set_RecyclerViewAadapterChange(viewPager: Int, context: Context) {
        val pref123 = PreferenceManager.getDefaultSharedPreferences(context)
        val editor123 = pref123.edit()
        editor123.putInt("RecyclerViewAadapterChange", viewPager)
            .apply()
        Log.d("DataModel", "save : ${viewPager}")
    }

    fun get_RecyclerViewAadapterChange(context: Context): Int {
        val pref123 = PreferenceManager.getDefaultSharedPreferences(context)
        val prefLoad123 = pref123.getInt("RecyclerViewAadapterChange", 0)
        Log.d("DataModel", "last saved : $prefLoad123")
        return prefLoad123

    }

}

