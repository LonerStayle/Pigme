package com.example.wisesaying.preference

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.wisesaying.db.entity.Pigme
import org.json.JSONArray
import org.json.JSONException

object Preference_dataModel {

    //새로운 명언을 추가하지 않은 기본 데이터들만 저장한 값
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

    // //새로운 명언을 추가하지 않은 기본 데이터들만 불러오는 값
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

    //명언이 추가되었을시에 데이터 재 저장
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

    ///명언이 추가되었을시에 데이터 저장한 값 불러오기
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



}