package com.example.goodfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goodfood.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val splashBinding:ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

       // splashBinding.tvAppName.text = "Add Food"
    }
}