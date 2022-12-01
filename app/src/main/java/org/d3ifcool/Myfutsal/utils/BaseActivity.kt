package org.d3ifcool.Myfutsal.utils

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.databinding.DialogProgressBinding

open class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog
    private var doubleBackToExitPressedOnce = false
    private lateinit var binding : DialogProgressBinding

    fun showErrorSnackBar(message : String, errorMessage: Boolean){
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view

        if(errorMessage){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackbar.show()
    }

    fun showProgressDialog() {

        binding = DialogProgressBinding.inflate(layoutInflater)

        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(binding.root)


        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}