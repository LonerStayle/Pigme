package com.example.wisesaying.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity
import com.example.wisesaying.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    //FIXME: handler = Hnadler() 로 변경
    private val handler by lazy{ Handler() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        //TODO : databinding 활용
        layout_splashlayout.setBackgroundResource(0)
        imageView_logoimage.setImageResource(R.drawable.pinklogo)

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

