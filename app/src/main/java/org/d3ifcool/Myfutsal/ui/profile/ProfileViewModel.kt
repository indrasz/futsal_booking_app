package org.d3ifcool.Myfutsal.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3ifcool.Myfutsal.data.model.User
import org.d3ifcool.Myfutsal.data.provider.user.UserProvider

class ProfileViewModel : ViewModel(){

    private val fireStore = UserProvider()

    fun initData() : LiveData<User> {
        val mutableData = MutableLiveData<User>()

        fireStore.getDataUserAccount().observeForever{ user ->
            mutableData.value = user
        }

        return mutableData
    }
}