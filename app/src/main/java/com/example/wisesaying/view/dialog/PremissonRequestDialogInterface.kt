package com.example.wisesaying.view.dialog

import android.app.AlertDialog
import android.app.Dialog
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
    val dialogPremissonRequstfrset = AlertDialog.Builder(context)
    // 권한요청 거절시 재설명
    val dialogPremissonRequstsecond = AlertDialog.Builder(context)

    val dialogImageSelect = Dialog(context,R.style.Theme_AppCompat_Light_Dialog_Alert)

// 권한요청 질문 다이얼로그
    val dialogPremissonRequstfrset_listner = DialogInterface.OnClickListener { dialog, which ->
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
            dialogPremissonRequstsecond.show()
        }
    }
}
    //권한요청 거절시 설명 다이얼로그
    val dialogPremissonRequstsecond_listner = DialogInterface.OnClickListener { dialog, which ->
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
        premissonRequestDialogInterface.dialogPremissonRequstfrset.setTitle(R.string.RequestPermissionTitle)
        premissonRequestDialogInterface.dialogPremissonRequstfrset.setMessage(R.string.RequestPermissionText)
        premissonRequestDialogInterface.dialogPremissonRequstfrset.setPositiveButton("네", dialogPremissonRequstfrset_listner)
        premissonRequestDialogInterface.dialogPremissonRequstfrset.setNegativeButton("아니오", dialogPremissonRequstfrset_listner)
        premissonRequestDialogInterface.dialogPremissonRequstsecond.setTitle(R.string.RequestPermissionTitle2)
        premissonRequestDialogInterface.dialogPremissonRequstsecond.setMessage(R.string.RequestPermissionText2)
        premissonRequestDialogInterface.dialogPremissonRequstsecond.setNegativeButton("네", dialogPremissonRequstsecond_listner)
    }

    fun dialogImageSelectBuilderSetting(premissonRequestDialogInterface:PremissonRequestDialogInterface){
        premissonRequestDialogInterface.dialogImageSelect.setContentView(R.layout.dialog_self_story_image_select_buttonevent)
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