package com.android.youtubeproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.android.youtubeproject.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    // ViewBinding 인스턴스 생성
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding을 초기화하고 뷰를 설정
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 애니메이션 로드 및 시작
//        val slideAndBounce = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        val slideAndBounce2 = AnimationUtils.loadAnimation(this, R.anim.anim_splash2)
        val slideAndBounce3 = AnimationUtils.loadAnimation(this, R.anim.anim_splash3)
//        binding.logo.startAnimation(slideAndBounce)
        binding.logo2.startAnimation(slideAndBounce2)
        binding.logo3.startAnimation(slideAndBounce3)


        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 5000) // 애니메이션이 끝난 후, 5초 후에 MainActivity로 이동
    }
}