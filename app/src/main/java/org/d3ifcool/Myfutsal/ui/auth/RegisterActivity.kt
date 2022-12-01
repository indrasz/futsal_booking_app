package org.d3ifcool.Myfutsal.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.data.model.User
import org.d3ifcool.Myfutsal.data.provider.user.UserProvider
import org.d3ifcool.Myfutsal.databinding.ActivityLoginBinding
import org.d3ifcool.Myfutsal.databinding.ActivityRegisterBinding
import org.d3ifcool.Myfutsal.ui.MainActivity
import org.d3ifcool.Myfutsal.utils.BaseActivity

class RegisterActivity : BaseActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            registerUser()
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

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.edtName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.hint_name), true)
                false
            }

            TextUtils.isEmpty(binding.edtEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.hint_email), true)
                false
            }

            TextUtils.isEmpty(binding.edtNoWhatsapp.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.hint_no_whatsapp), true)
                false
            }

            TextUtils.isEmpty(binding.edtPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.hint_password), true)
                false
            }

            TextUtils.isEmpty(binding.edtCpassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.hint_password),
                    true
                )
                false
            }

            binding.edtPassword.text.toString().trim { it <= ' ' } != binding.edtCpassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.hint_err_password),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    private fun registerUser() {

        if (validateRegisterDetails()) {

            showProgressDialog()

            val email: String = binding.edtEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.edtPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user = User(
                            firebaseUser.uid,
                            binding.edtName.text.toString().trim { it <= ' ' },
                            binding.edtEmail.text.toString().trim { it <= ' ' },
                            binding.edtNoWhatsapp.text.toString().trim { it <= ' ' }
                        )

                        UserProvider().registerUser(this@RegisterActivity, user)

                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    fun userRegistrationSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()

        FirebaseAuth.getInstance().signOut()
        finish()
    }
}