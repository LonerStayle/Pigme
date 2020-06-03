package com.example.wisesaying.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.wisesaying.Preference
import com.example.wisesaying.Dialog
import com.example.wisesaying.view.fragment.FragmentSetting
import com.example.wisesaying.R
import com.example.wisesaying.databinding.ActivityMainBinding
import com.example.wisesaying.view.fragment.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity() {
    private val preference = Preference()
private val handler by lazy {Handler()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainFragment.requestPermissionScore = preference.get_Permission(applicationContext)
        DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main)

        val dialog = Dialog(this, supportFragmentManager)

        dialog.buliderStart()
        dialog.buliderStart2()

        if(MainFragment.requestPermissionScore == 0 || MainFragment.requestPermissionScore == 2 )
        dialog.builder.show()


        // 권한 요청 선택결과에 따라 프레그먼트에 기록하기 위한 프레그먼트 실행, 현재 Visivble 모드
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, MainFragment())
        transaction.commit()
    }


    override fun onBackPressed() {
// 기본적으로 프레그먼트 셋팅 버튼이 프레그먼트 매니저를 이용한 commit이 된 상태 mainFragment 화면에서 gone 처리 되어있어서 안보임.
        //프레그먼트 셋팅 버튼의 addbackstack를 사용한 상태라서 뒤로가기를 기본적으로 두번~세번 해야 앱이 종료됨

       MainFragment.fragmentSettingClickCount = 0

        //뒤로가기 버튼 누르는 순간 앱을 종료하기전에 셋팅창 키는 버튼이 아예 죽어버림으로 한번 더 프레그먼트 매니저 사용
        val fragmentSetting_transaction = supportFragmentManager.beginTransaction()
        fragmentSetting_transaction.replace(
            R.id.fregment_SettingLayout,
            FragmentSetting()
        )
        fregment_SettingLayout.visibility = View.GONE
        fragmentSetting_transaction.commit()


if( supportFragmentManager.backStackEntryCount == 1  )
    Toast.makeText(this,R.string.toast,Toast.LENGTH_SHORT).show()


        super.onBackPressed()
    }


    override fun onStop() {
        MainFragment.fragmentSettingClickCount = 0
        fregment_SettingLayout.visibility = View.GONE
        super.onStop()
    }

    override fun onDestroy() {
        val viewPagerPosition = viewPager.currentItem
        preference.set_CurrentViewpager(viewPagerPosition, this)

        //위에 5초전에 종료를 한다면 핸들러 때문에 에러 문구가 안뜨도록 핸들러 메세지 제거
       handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }


}
