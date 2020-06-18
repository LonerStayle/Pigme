package com.example.wisesaying.view.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.wisesaying.view.fragment.FragmentSetting
import com.example.wisesaying.R
import com.example.wisesaying.databinding.ActivityMainBinding
import com.example.wisesaying.preference.PrefSingleton
import com.example.wisesaying.view.dialog.DialogSimple
import com.example.wisesaying.view.fragment.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.*

/**
 * 리팩토링 시작
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // 권한 요청 선택결과에 따라 프레그먼트에 기록하기 위한 프레그먼트 실행, 현재 Visivble 모드
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, MainFragment())
            .commit()

        //최초 실행시 requestPermissonScore에 따라 다이얼로그 띄우기
        if (PrefSingleton.getInstance(this).requestScore == 0 || PrefSingleton.getInstance(this).requestScore == 2)

            DialogSimple.show(this,
                R.string.dialogRequestPermissionTitle,
                R.string.dialogRequestPermissionText,
                "네",
                {
                    PrefSingleton.getInstance(this).requestScore = 1
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                    if (fragment is FragmentSetting) {
                        fragment.switchWidget_Setting_Premisson.isChecked = true
                    }
                },
                "아니오",
                {
                    PrefSingleton.getInstance(this).requestScore = 2
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                    if (fragment is FragmentSetting) {
                        fragment.switchWidget_Setting_Premisson.isChecked = false

                        val secondDialog = AlertDialog.Builder(this)
                            .setTitle(R.string.dialogRequestPermissionTitle2)
                            .setMessage(R.string.dialogRequestPermissionText2)
                            .setPositiveButton("네") { _, _ ->
                                return@setPositiveButton
                            }
                        secondDialog.show()
                    }
                })
    }


    override fun onBackPressed() {
        /**
         * 기본적으로 프레그먼트 셋팅 레이아웃이 프레그먼트 매니저를 이용한 commit이 된 상태임, mainFragment 맨 처음 화면에서 gone 처리 되어있어서 안보일 뿐임.
         *  그래서 프레그먼트 셋팅 레이아웃에서 addtobackstack(null)를 사용한 상태라서 뒤로가기를 기본적으로 두번~세번 해야 앱이 종료됨
         */


        /**
         *  뒤로가기 버튼 누르면 앱이 바로 종료되지 않고, 프레그먼트 셋팅 레이아웃이 메모리상에서 제거됨 그래서 프레그먼트 레이아웃을 다시 키려고 하면
         *  버튼 클릭 리스너가 적용되질 않음
         *
         *  한번 더 프레그먼트 매니저로 addtoBackstack(null) 사용 구문만 없이 다시 커밋함
         */

        val fragmentSettingTransaction = supportFragmentManager.beginTransaction()
        fragmentSettingTransaction.replace(
            R.id.fregment_SettingLayout,
            FragmentSetting()
        )
        fregment_SettingLayout.visibility = View.GONE
        fragmentSettingTransaction.commit()

        /**
         * 앱을 종료시키지 않았을 시에 프레그먼트 셋팅 레이아웃에 .addtoBackStack(null) 추가함
         * 사용자가 종료하지 않고 이어서 계속 사용한다면 어이없이 어플이 종료되지 않도록 유도함
         */
        if (supportFragmentManager.backStackEntryCount == 1) {
            Toast.makeText(this, R.string.toast_AppExitLastText, Toast.LENGTH_SHORT).show()
            if (!isFinishing) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(5000)
                    fragmentSettingTransaction.remove(FragmentSetting())
                    val fragmentSettingTransactionReCreate =
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fregment_SettingLayout,
                                FragmentSetting()
                            )
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                }
            }
        }
        super.onBackPressed()
    }

    override fun onStop() {

        fregment_SettingLayout.visibility = View.GONE
        super.onStop()

    }

    override fun onDestroy() {
        PrefSingleton.getInstance(this).currentViewpager = viewPager.currentItem
        super.onDestroy()
    }
}

