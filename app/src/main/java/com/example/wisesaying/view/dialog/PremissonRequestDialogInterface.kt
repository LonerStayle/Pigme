package com.example.wisesaying.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import com.example.wisesaying.R
import com.example.wisesaying.preference.PrefSingleton
import com.example.wisesaying.usagemarks.UsageMarksScore
import com.example.wisesaying.view.fragment.FragmentSetting
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * TODO: view / diolog 패키지로 이동
 *
 * FIXME: Dialog 클래스명 의미 와닿게 변경 ex) PermissoinrequestDialog
 * FIXME: supportFragmentManager 패러미터가 필요가 없음 콘텍스트 하위로 해서 사용해도 됨
 */
class PremissonRequestDialogInterface(context: AppCompatActivity) {

    // 권한요청 질문 묻기
    val dialogPremissonRequstfrset = AlertDialog.Builder(context)

    // 권한요청 거절시 재설명
    val dialogPremissonRequstsecond = AlertDialog.Builder(context)

    //ImageSelecLastDialog
    val dialogImageSelect = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)

    // 권한요청 질문 다이얼로그
  private  val dialogPremissonRequstfrset_listner = DialogInterface.OnClickListener { _, which ->
        when (which) {
            /**
             * 이늄 클래스 와 when 같이 쓰면 좋음
             */
            DialogInterface.BUTTON_POSITIVE -> {
                UsageMarksScore.requestPermissionScore = 1
                PrefSingleton.getInstance(context).requestScore = 1
                val fragment =
                    context.supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                if (fragment is FragmentSetting) {
                    fragment.switchWidget_Setting_Premisson.isChecked = true
                }

            }
            DialogInterface.BUTTON_NEGATIVE -> {
                UsageMarksScore.requestPermissionScore = 2
                PrefSingleton.getInstance(context).requestScore = 2
                dialogPremissonRequstsecond.show()
            }
        }

    }

    //권한요청 거절시 설명 다이얼로그
    private val dialogPremissonRequstsecond_listner = DialogInterface.OnClickListener { _, which ->
        when (which) {
            DialogInterface.BUTTON_NEGATIVE -> {
                UsageMarksScore.requestPermissionScore = 2
                PrefSingleton.getInstance(context).requestScore = 2
                val fragment =
                    context.supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                if (fragment is FragmentSetting) {
                    fragment.switchWidget_Setting_Premisson.isChecked = false
                }
            }
        }
    }


    /**
     *
     * FIXME: 함수 체어이닝 기법사용하기
     */
    fun dialogfrestAndSecondBuilderSetting(premissonRequestDialogInterface: PremissonRequestDialogInterface) {

        premissonRequestDialogInterface.dialogPremissonRequstfrset.setTitle(R.string.RequestPermissionTitle)
            .setMessage(R.string.RequestPermissionText)
            .setPositiveButton("네", dialogPremissonRequstfrset_listner)
            .setNegativeButton("아니오", dialogPremissonRequstfrset_listner)
        premissonRequestDialogInterface.dialogPremissonRequstsecond.setTitle(R.string.RequestPermissionTitle2)
            .setMessage(R.string.RequestPermissionText2)
            .setNegativeButton(
                "네",
                dialogPremissonRequstsecond_listner
            )
    }

    fun dialogImageSelectBuilderSetting(premissonRequestDialogInterface: PremissonRequestDialogInterface) {
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