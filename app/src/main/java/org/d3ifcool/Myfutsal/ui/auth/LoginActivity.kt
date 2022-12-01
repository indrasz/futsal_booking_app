package org.d3ifcool.Myfutsal.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3ifcool.Myfutsal.databinding.ActivityLoginBinding
import org.d3ifcool.Myfutsal.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(
                Intent(
                    baseContext, MainActivity::class.java
                )
            )
        }

        binding.tvRegister.setOnClickListener {
            startActivity(
                Intent(
                    baseContext, RegisterActivity::class.java
                )
            )
        }

        supportActionBar?.hide()
    }
}