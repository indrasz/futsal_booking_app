package org.d3ifcool.Myfutsal.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.data.model.User
import org.d3ifcool.Myfutsal.data.provider.user.UserProvider
import org.d3ifcool.Myfutsal.databinding.ActivityLoginBinding
import org.d3ifcool.Myfutsal.ui.MainActivity
import org.d3ifcool.Myfutsal.utils.BaseActivity

class LoginActivity :  BaseActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            logInRegisteredUser()
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

    //cek apakah input sudah sesuai
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.edtEmail.text.toString().trim { it <= ' ' }) -> {
                //jika kosong maka muncul warning
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.edtPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            showProgressDialog()

            val email = binding.edtEmail.text.toString().trim { it <= ' ' }
            val password = binding.edtPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        UserProvider().getUserDetails(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    fun userLoggedInSuccess(user: User) {

        hideProgressDialog()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
}