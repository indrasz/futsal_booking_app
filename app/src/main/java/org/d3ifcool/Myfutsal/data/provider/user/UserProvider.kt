package org.d3ifcool.Myfutsal.data.provider.user

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.d3ifcool.Myfutsal.data.model.User
import org.d3ifcool.Myfutsal.ui.auth.LoginActivity
import org.d3ifcool.Myfutsal.ui.auth.RegisterActivity
import org.d3ifcool.Myfutsal.ui.profile.ProfileFragment
import org.d3ifcool.Myfutsal.utils.Constants
import java.io.IOException

class UserProvider {

    private val mFireStore = FirebaseFirestore.getInstance()

    //untuk mengambil id user
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    //untuk registrasi user
    fun registerUser(activity: RegisterActivity, userInfo: User) {

        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }

    fun getUserDetails(activity: Activity) {

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYFUTSAL_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    user.name
                )
                editor.apply()

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }

            }
            .addOnFailureListener { e ->
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    //fungsi untuk mengambil data user dari firebase
    fun getDataUserAccount(): LiveData<User?> {

        val fragment : Fragment = ProfileFragment()
        val userData = MutableLiveData<User>()

        try {
            mFireStore.collection(Constants.USERS)
                .document(getCurrentUserID())
                .get()
                .addOnSuccessListener { document ->

                    Log.i(fragment.javaClass.simpleName, document.toString())

                    val user = document.toObject(User::class.java)!!

                    val sharedPreferences =
                        fragment.activity?.getSharedPreferences(
                            Constants.MYFUTSAL_PREFERENCES,
                            Context.MODE_PRIVATE
                        )

                    val editor = sharedPreferences?.edit()
                    editor?.putString(
                        Constants.LOGGED_IN_USERNAME,
                        user.name
                    )
                    editor?.apply()

                    userData.value = user

                }
                .addOnFailureListener { e ->
                    Log.e(
                        fragment.javaClass.simpleName,
                        "Error while getting user details.",
                        e
                    )
                }

        } catch (e : IOException){
            e.printStackTrace()
        }

        return userData
    }

}