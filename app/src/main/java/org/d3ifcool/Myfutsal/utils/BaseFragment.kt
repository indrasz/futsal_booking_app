package org.d3ifcool.Myfutsal.utils

import android.app.Dialog
import androidx.fragment.app.Fragment
import org.d3ifcool.Myfutsal.databinding.DialogProgressBinding

open class BaseFragment : Fragment() {

    private lateinit var mProgressDialog: Dialog
    private lateinit var binding : DialogProgressBinding

    fun showProgressDialog() {

        binding = DialogProgressBinding.inflate(layoutInflater)

        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(binding.root)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}