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
import com.example.wisesaying.preference.PrefRequestPremisson
import com.example.wisesaying.preference.PrefViewPagerItem
import com.example.wisesaying.view.constscore.UsageMark
import com.example.wisesaying.view.dialog.DialogSimple
import com.example.wisesaying.view.fragment.*
import com.example.wisesaying.view.toast.toastShort
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
        transaction.replace(R.id.constraintLayout, MainFragment())
            .commit()

        //최초 실행시 requestPermissonScore에 따라 다이얼로그 띄우기
        if (PrefRequestPremisson.getInstance(this).requestScore == UsageMark.REQUEST_NEGATIVE)

            DialogSimple.show(this,
                R.string.dialogRequestPermissionTitle,
                R.string.dialogRequestPermissionText,
                R.string.PositiveMassage,
                {
                    PrefRequestPremisson.getInstance(this).requestScore =
                        UsageMark.REQUEST_POSITIVE
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                    if (fragment is FragmentSetting) {
                        fragment.switchWidget_Setting_Premisson.isChecked = true
                    }
                },
                R.string.NegativeMassage,
                {
                    PrefRequestPremisson.getInstance(this).requestScore =
                        UsageMark.REQUEST_NEGATIVE
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.fregment_SettingLayout)
                    if (fragment is FragmentSetting) {
                        fragment.switchWidget_Setting_Premisson.isChecked = false
                    }
                        val secondDialog = AlertDialog.Builder(this)
                            .setTitle(R.string.dialogRequestPermissionTitle2)
                            .setMessage(R.string.dialogRequestPermissionText2)
                            .setPositiveButton("네") { _, _ ->
                                return@setPositiveButton
                            }
                        secondDialog.show()


                })
    }


    override fun onBackPressed() {
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
            toastShort(this, R.string.toast_AppExitLastText)
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
        PrefViewPagerItem.getInstance(this).currentViewpager = viewPager.currentItem
        super.onDestroy()

    }
}

