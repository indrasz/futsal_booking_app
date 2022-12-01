package org.d3ifcool.Myfutsal.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.databinding.ActivityLoginBinding
import org.d3ifcool.Myfutsal.databinding.ActivityRegisterBinding
import org.d3ifcool.Myfutsal.ui.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(
                Intent(
                    baseContext, LoginActivity::class.java
                )
            )
        }

        binding.tvLogin.setOnClickListener {
            startActivity(
                Intent(
                    baseContext, LoginActivity::class.java
                )
            )
        }

        supportActionBar?.hide()
    }
}