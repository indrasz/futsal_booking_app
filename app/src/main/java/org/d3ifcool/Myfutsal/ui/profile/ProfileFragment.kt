package org.d3ifcool.Myfutsal.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.data.model.User
import org.d3ifcool.Myfutsal.databinding.FragmentProfileBinding
import org.d3ifcool.Myfutsal.ui.auth.LoginActivity
import org.d3ifcool.Myfutsal.utils.Constants

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var mUserDetails: User //variable user
    private lateinit var auth: FirebaseAuth

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()

        val authCheck = FirebaseAuth.getInstance().currentUser
        if(authCheck != null) {
            getUserDetails()

        } else {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.apply {

            btnLogout.setOnClickListener{
                auth.signOut()
                Intent(activity, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
    }
    private fun getUserDetails() = viewModel.initData().observe(requireActivity()) {
        userDetailsSuccess(it)
    }

    private fun userDetailsSuccess(user: User?) {
        if (user == null)  return
        else {
            user.also { mUserDetails = it }
            binding.tvUsername.text = user.name
        }
    }

}