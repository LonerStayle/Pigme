package com.example.wisesaying.preference

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.wisesaying.db.entity.Pigme
import org.json.JSONArray
import org.json.JSONException

class PrefUsageMark private constructor(context: Context) {

    enum class Key {
        SELF_STORY_USAGEMARK,
        LIVE_DATA_FIRST_USETRACE,
        DELETE_MODEL_LIST,
        SELF_STORY_DELETE_MODE_TO_MAIN_FRAGMENT_INSERT_DATA_PASSING_EDIT_TEXT_IMAGE_SELECT_SELF_STORY_IN_TEXT,
        SELF_STORY_DELETE_MODE_TO_MAIN_FRAGMENT_INSERT_DATA_PASSING_TEXT_VIEW_IMAGE_BACK_GROUND_RESID_CHECK
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

    var liveDataFirstUseTrace: Boolean
        get() = pref.getBoolean(Key.LIVE_DATA_FIRST_USETRACE.name, true)
        set(value) {
            pref.edit().putBoolean(Key.LIVE_DATA_FIRST_USETRACE.name, value).apply()
        }

    var deleteModelListOfIndex: MutableList<Int>
        get() {
            val index = pref.getString(Key.DELETE_MODEL_LIST.name, null)


            val deletionListOfIndex = mutableListOf<Int>()
            if (index != null) {
                try {
                    val jsonIndex = JSONArray(index)


                    for (i in 0 until jsonIndex.length()) {
                        val urlIndex = jsonIndex.optString(i).toInt()

                        deletionListOfIndex.add(i, urlIndex)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return deletionListOfIndex
        }
        set(value) {

            val index = JSONArray()


            for (i in value.indices) {
                index.put(value[i])
            }
            if (value.isNotEmpty()) {
                pref.edit().putString(Key.DELETE_MODEL_LIST.name, index.toString())
                    .apply()
            } else {
                pref.edit().putString(Key.DELETE_MODEL_LIST.name, null)
                    .apply()
            }
        }
    var selfStoryDeleteAfterInsertDataText: String
        get() = pref.getString(
            Key.SELF_STORY_DELETE_MODE_TO_MAIN_FRAGMENT_INSERT_DATA_PASSING_EDIT_TEXT_IMAGE_SELECT_SELF_STORY_IN_TEXT.name,
            null
        )!!
        set(value) {
            pref.edit().putString(
                Key.SELF_STORY_DELETE_MODE_TO_MAIN_FRAGMENT_INSERT_DATA_PASSING_EDIT_TEXT_IMAGE_SELECT_SELF_STORY_IN_TEXT.name,
                value
            )
                .apply()
        }
    var selfStoryDeleteAfterInsertDataImage: String
        get() = pref.getString(
            Key.SELF_STORY_DELETE_MODE_TO_MAIN_FRAGMENT_INSERT_DATA_PASSING_TEXT_VIEW_IMAGE_BACK_GROUND_RESID_CHECK.name,
            null
        )!!
        set(value) {
            pref.edit().putString(
                Key.SELF_STORY_DELETE_MODE_TO_MAIN_FRAGMENT_INSERT_DATA_PASSING_TEXT_VIEW_IMAGE_BACK_GROUND_RESID_CHECK.name,
                value
            ).apply()
        }


}