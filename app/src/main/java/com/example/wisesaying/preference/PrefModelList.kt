package com.example.wisesaying.preference

import android.content.Context
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.wisesaying.db.entity.Pigme
import org.json.JSONArray
import org.json.JSONException

class PrefModelList private constructor(context:Context){

    companion object{
        private var instance:PrefModelList? = null
        fun getInstance(context: Context):PrefModelList = instance?: PrefModelList(context).also {
            instance = it
        }

    }

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    var modelListPref: List<Pigme>
        get() {
            val jsonTextString = pref.getString(PrefRequestPremisson.Key.MODELLIST_PREF_TEXT_STORY.name, null)
            val jsonImage = pref.getString(PrefRequestPremisson.Key.MODELLIST_PREF_IMAGE.name, null)

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
                pref.edit().putString(PrefRequestPremisson.Key.MODELLIST_PREF_TEXT_STORY.name, textStory.toString())
                    .putString(PrefRequestPremisson.Key.MODELLIST_PREF_IMAGE.name, image.toString())
                    .apply()
            } else {
                pref.edit().putString(PrefRequestPremisson.Key.MODELLIST_PREF_TEXT_STORY.name, null)
                    .putString(PrefRequestPremisson.Key.MODELLIST_PREF_IMAGE.name, null)
                    .apply()
            }
        }


}