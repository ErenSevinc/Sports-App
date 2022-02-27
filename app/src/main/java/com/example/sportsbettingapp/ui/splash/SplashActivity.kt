package com.example.sportsbettingapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.sportsbettingapp.R
import com.example.sportsbettingapp.databinding.ActivitySplashBinding
import com.example.sportsbettingapp.ui.home.HomeActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val animationBallEmpty = AnimationUtils.loadAnimation(this, R.anim.animation1)
        val animationBall = AnimationUtils.loadAnimation(this, R.anim.animation2)
        val animationText = AnimationUtils.loadAnimation(this, R.anim.animation3)

        binding.imageView.animation = animationBallEmpty
        binding.imageView2.animation = animationBall
        binding.textView.animation = animationText

        Handler().postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        },5000.toLong())
    }
}