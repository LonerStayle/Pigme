package com.example.wisesaying.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.wisesaying.R
import com.example.wisesaying.databinding.ActivityMainBinding
import com.example.wisesaying.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    /**
     *  handler = Hnadler() 로 변경완료
     */

    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding= DataBindingUtil.setContentView<ActivitySplashBinding>(this,R.layout.activity_splash)

        binding.layoutSplashlayout.setBackgroundResource(0)
        binding.imageViewLogoimage.setImageResource(R.drawable.pinklogo)
        //TODO : databinding 활용


        /** splash 화면 목록
          layout_splashlayout.setBackgroundResource(R.drawable.splashbackgroundpink)

          layout_splashlayout.setBackgroundResource(R.drawable.splashbackgroundpupple)

          layout_splashlayout.setBackgroundResource(R.drawable.splashbackgroundyellow)
          imageView_logoimage.setImageResource(R.drawable.pupplelogo)

          layout_splashlayout.setBackgroundResource(R.drawable.splashbackground)
          imageView_logoimage.setImageResource(R.drawable.logo)*/



// TODO: 코루틴 활용하면 좋음.
        // 핸들러는 무거움
        handler.postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }


    override fun onBackPressed() {
        handler.removeCallbacksAndMessages(null)
        super.onBackPressed()
    }


}

