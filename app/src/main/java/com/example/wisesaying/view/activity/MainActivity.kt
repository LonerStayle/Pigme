package com.example.wisesaying.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.wisesaying.view.fragment.FragmentSetting
import com.example.wisesaying.R
import com.example.wisesaying.databinding.ActivityMainBinding
import com.example.wisesaying.preference.Preference_View
import com.example.wisesaying.preference.PreferenceinPermissonRequest
import com.example.wisesaying.view.dialog.PremissonRequestDialog
import com.example.wisesaying.view.dialog.PremissonRequestDialogInterface
import com.example.wisesaying.view.fragment.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*

/**
 * 리팩토링 시작 
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainFragment.requestPermissionScore = PreferenceinPermissonRequest.get_PermissionRequestScore(this)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        //최초 실행시 requestPermissonScore에 따라 다이얼로그 띄우기
        if(MainFragment.requestPermissionScore == 0 || MainFragment.requestPermissionScore == 2 )
        {
            val dialogInterface = PremissonRequestDialogInterface(this,supportFragmentManager)
            dialogInterface.dialogfrestAndSecondBuilderSetting(dialogInterface)
            dialogInterface.dialogfrestbuilder.show()
        }

        // 권한 요청 선택결과에 따라 프레그먼트에 기록하기 위한 프레그먼트 실행, 현재 Visivble 모드
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, MainFragment())
        transaction.commit()
    }


    override fun onBackPressed() {
        /**
         * 기본적으로 프레그먼트 셋팅 레이아웃이 프레그먼트 매니저를 이용한 commit이 된 상태임, mainFragment 맨 처음 화면에서 gone 처리 되어있어서 안보일 뿐임.
         *  그래서 프레그먼트 셋팅 레이아웃에서 addtobackstack(null)를 사용한 상태라서 뒤로가기를 기본적으로 두번~세번 해야 앱이 종료됨
         */

        MainFragment.fragmentSettingClickCount = 0

        /**
         *  뒤로가기 버튼 누르면 앱이 바로 종료되지 않고, 프레그먼트 셋팅 레이아웃이 메모리상에서 제거됨 그래서 프레그먼트 레이아웃을 다시 키려고 하면
         *  버튼 클릭 리스너가 적용되질 않음
         *
         *  한번 더 프레그먼트 매니저로 addtoBackstack(null) 사용 구문만 없이 다시 커밋함
         */

        val fragmentSetting_transaction = supportFragmentManager.beginTransaction()
        fragmentSetting_transaction.replace(
            R.id.fregment_SettingLayout,
            FragmentSetting()
        )
        fregment_SettingLayout.visibility = View.GONE
        fragmentSetting_transaction.commit()

        /**
         * 앱을 종료시키지 않았을 시에 프레그먼트 셋팅 레이아웃에 .addtoBackStack(null) 추가함
         * 사용자가 종료하지 않고 이어서 계속 사용한다면 어이없이 어플이 종료되지 않도록 유도함
         */
        if (supportFragmentManager.backStackEntryCount == 1){
            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show()
          if (!isFinishing) {
            CoroutineScope(Dispatchers.Main + Job()).launch {
                delay(3000)
                fragmentSetting_transaction.remove(FragmentSetting())
                val fragmentSetting_Transaction_ReCreate = supportFragmentManager.beginTransaction()
                fragmentSetting_Transaction_ReCreate.replace(
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
        MainFragment.fragmentSettingClickCount = 0
        fregment_SettingLayout.visibility = View.GONE
        super.onStop()
    }

    override fun onDestroy() {
        val viewPagerPosition = viewPager.currentItem
        Preference_View.set_CurrentViewpager(viewPagerPosition, this)

        super.onDestroy()
    }
}