package org.d3ifcool.Myfutsal.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import org.d3ifcool.Myfutsal.ui.MainActivity
import org.d3ifcool.Myfutsal.databinding.ActivitySplashBinding
import org.d3ifcool.Myfutsal.ui.auth.LoginActivity
import org.d3ifcool.Myfutsal.utils.Constants.SPLASH_SCREEN_TAG

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    startActivity(Intent(baseContext, LoginActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Log.d(SPLASH_SCREEN_TAG, e.message.toString())
                }
            }
        }.start()

        supportActionBar?.hide()
    }
}