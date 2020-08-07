package com.example.pigme.view.activity

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pigme.R
import com.example.pigme.databinding.ActivitySplashBinding
import kotlinx.coroutines.*


class SplashActivity : AppCompatActivity() {

    /**
     *  handler = Hnadler() 로 변경완료
     *  현재는 주석처리 핸들러 -> 코루틴 으로 변경함
     */

    // private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         *  databinding 활용 완료
         */
       val binding= DataBindingUtil.setContentView<ActivitySplashBinding>(this,R.layout.activity_splash)

        binding.constraintLayoutSplashlayout.setBackgroundResource(0)
        binding.imageViewLogoimage.setImageResource(R.drawable.pinklogo)


        /** splash 화면 목록
          layout_splashlayout.setBackgroundResource(R.drawable.splashbackgroundpink)

          layout_splashlayout.setBackgroundResource(R.drawable.splashbackgroundpupple)

          layout_splashlayout.setBackgroundResource(R.drawable.splashbackgroundyellow)
          imageView_logoimage.setImageResource(R.drawable.pupplelogo)

          layout_splashlayout.setBackgroundResource(R.drawable.splashbackground)
          imageView_logoimage.setImageResource(R.drawable.logo)*/



        // 핸들러는 무겁기 때문에 코루틴으로 변경
//        handler.postDelayed({
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        },3000)

        /**
         *  핸들러 - > 코루틴으로 변경 완료
         */
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            if(!isFinishing) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


//    override fun onBackPressed() {
//        handler.removeCallbacksAndMessages(null)
//        super.onBackPressed()
//    }


}

