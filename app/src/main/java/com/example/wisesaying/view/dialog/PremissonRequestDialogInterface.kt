package com.example.wisesaying.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import com.example.wisesaying.R
import com.example.wisesaying.preference.PreferenceinPermissonRequest
import com.example.wisesaying.view.fragment.FragmentSetting
import com.example.wisesaying.view.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * TODO: view / diolog 패키지로 이동
 *
 * FIXME: Dialog 클래스명 의미 와닿게 변경 ex) PermissoinrequestDialog
 */
class PremissonRequestDialogInterface(var context: Context, supportFragmentManager: FragmentManager){

    // 권한요청 질문 묻기
    val dialogfrestbuilder = AlertDialog.Builder(context)
    // 권한요청 거절시 재설명
    val dialogSecondbuilder = AlertDialog.Builder(context)

// 권한요청 질문 다이얼로그
    val dialog_frest_listener = DialogInterface.OnClickListener { dialog, which ->
    when (which) {
        /**
         * 이늄 클래스 와 when 같이 쓰면 좋음
         */
        DialogInterface.BUTTON_POSITIVE -> {
            MainFragment.requestPermissionScore = 1
            PreferenceinPermissonRequest.set_PermissionRequestScore(1,context)
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
            if (fragment is FragmentSetting) {
                fragment.switchWidget_Setting_Premisson.isChecked = true
            }

        }
        DialogInterface.BUTTON_NEGATIVE -> {
            MainFragment.requestPermissionScore = 2
            PreferenceinPermissonRequest.set_PermissionRequestScore(2,context)
           dialogSecondbuilder.show()
        }
    }
}

    //권한요청 거절시 설명 다이얼로그
    val dialog_second_listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> {
                    MainFragment.requestPermissionScore = 2
                    PreferenceinPermissonRequest.set_PermissionRequestScore(2,context)
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                    if (fragment is FragmentSetting) {
                        fragment.switchWidget_Setting_Premisson.isChecked = false
                    }
                }
            }
        }


    /**
     * FIXME: 함수명 다시짓기, 아예 함수안에서 다 해결해버리기  혹은 오브젝트 사용
     */
    fun dialogfrestAndSecondBuilderSetting (premissonRequestDialogInterface:PremissonRequestDialogInterface) {
        premissonRequestDialogInterface.dialogfrestbuilder.setTitle(R.string.RequestPermissionTitle)
        premissonRequestDialogInterface.dialogfrestbuilder.setMessage(R.string.RequestPermissionText)
        premissonRequestDialogInterface.dialogfrestbuilder.setPositiveButton("네", dialog_frest_listener)
        premissonRequestDialogInterface.dialogfrestbuilder.setNegativeButton("아니오", dialog_frest_listener)
        premissonRequestDialogInterface.dialogSecondbuilder.setTitle(R.string.RequestPermissionTitle2)
        premissonRequestDialogInterface.dialogSecondbuilder.setMessage(R.string.RequestPermissionText2)
        premissonRequestDialogInterface.dialogSecondbuilder.setNegativeButton("네", dialog_second_listener)
    }

    /**
     * 함수안에서 해결 할경우 show
     */
//    fun show(condition: Boolean){
//        if(condition){
//            AlertDialog.Builder(context)
//                .create()
//                .show()
//        }
//    }

}