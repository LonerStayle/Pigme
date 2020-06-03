package com.example.wisesaying

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import com.example.wisesaying.view.fragment.FragmentSetting
import com.example.wisesaying.view.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * TODO: view / diolog 패키지로 이동
 *
 * FIXME: Dialog 클래스명 의미 와닿게 변경 ex) PermissoinrequestDialog
 */
class Dialog(var context: Context, supportFragmentManager: FragmentManager){

    val preference = Preference()
    // 권한요청 질문 묻기
    val builder = AlertDialog.Builder(context)
    // 권한요청 거절시 재설명
    val builder2 = AlertDialog.Builder(context)


// 권한요청 질문 다이얼로그
    val dialog_listener = DialogInterface.OnClickListener { dialog, which ->
    when (which) {
        /**
         * 이늄 클래스 와 when 같이 쓰면 좋음
         */
        DialogInterface.BUTTON_POSITIVE -> {
            MainFragment.requestPermissionScore = 1
            preference.set_Permission(1, context)
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
            if (fragment is FragmentSetting) {
                fragment.switchWidget_Setting_Premisson.isChecked = true
            }

        }
        DialogInterface.BUTTON_NEGATIVE -> {
            MainFragment.requestPermissionScore = 2
            preference.set_Permission(2, context)
            builder2.show()
        }
    }
}

    //권한요청 거절시 설명 다이얼로그
    val dialog_listener2 = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> {
                    MainFragment.requestPermissionScore = 2
                    preference.set_Permission(2, context)
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                    if (fragment is FragmentSetting) {
                        fragment.switchWidget_Setting_Premisson.isChecked = false
                    }
                }
            }
        }
    }

    /**
     * FIXME: 함수명 다시짓기, 아예 함수안에서 다 해결해버리기  혹은 오브젝트 사용
     */
    fun buliderStart () {
        builder.setTitle(R.string.RequestPermissionTitle)
        builder.setMessage(R.string.RequestPermissionText)
        builder.setPositiveButton("네", dialog_listener)
        builder.setNegativeButton("아니오", dialog_listener)
    }

    fun buliderStart2 () {
        builder2.setTitle(R.string.RequestPermissionTitle2)
       builder2.setMessage(R.string.RequestPermissionText2)
       builder2.setNegativeButton("네", dialog_listener2)
    }

    /**
     * 함수안에서 해결 할경우 show
     */
    fun show(condition: Boolean){
        if(condition){
            AlertDialog.Builder(context)
                .create()
                .show()
        }
    }

}