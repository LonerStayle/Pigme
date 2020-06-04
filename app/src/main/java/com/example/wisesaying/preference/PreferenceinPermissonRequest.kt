package com.example.wisesaying.preference

import android.content.Context
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import com.example.wisesaying.db.entity.Pigme

import kotlinx.android.synthetic.main.fragment_settings.view.*
import org.json.JSONArray
import org.json.JSONException

/**
 * class -> object 로 변경
 */
object PreferenceinPermissonRequest {
    /**
     *preference 분리 완료
     */

//    var permission:Int
//    get() {}
//    set(value) {}

    //앱 자동실행 권한 요청 저장
    fun set_PermissionRequestScore(score: Int, context: Context) {
        val setPermissonPref =
            PreferenceManager.getDefaultSharedPreferences(context)
        val setPermissonPrefEdit = setPermissonPref.edit()
        setPermissonPrefEdit.putInt("saveData", score)
            .apply()
    }

    //앱 자동실행 권한 요청 상태 불러오기
    fun get_PermissionRequestScore(context: Context): Int {
        val get_Permisson_Pref = PreferenceManager.getDefaultSharedPreferences(context)
        return get_Permisson_Pref.getInt("saveData", 0)
    }



}

