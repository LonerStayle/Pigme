package com.example.pigme.view.dialog

import android.app.Dialog
import android.content.Context
import com.example.pigme.R


/**
 * TODO: view / diolog 패키지로 이동
 *
 * FIXME: Dialog 클래스명 의미 와닿게 변경 ex) PermissoinrequestDialog
 * FIXME: supportFragmentManager 패러미터가 필요가 없음 콘텍스트 하위로 해서 사용해도 됨
 */
class DialogInLayoutCreateMode(context: Context) {


    val dialogImageSelect = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
    val dialogInImageDeleteDialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)

    fun dialogImageSelectBuilderSetting(dialogInLayoutCreateMode: DialogInLayoutCreateMode) {
        dialogInLayoutCreateMode.dialogImageSelect.setContentView(R.layout.dialog_self_story_image_select_buttonevent)
        dialogInLayoutCreateMode.dialogInImageDeleteDialog.setContentView(R.layout.dialog_dialogindialog_deletelist)
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