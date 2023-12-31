package com.example.sipami.api.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sipami.api.repository.__user___

class User : ViewModel() {
    private val createUserResult = MutableLiveData<Boolean>()
    private val __user = __user___()

    fun createUserWithEmailAndPassword(email: String, password: String) {
        __user.createUserWithEmailAndPassword(email, password) { isSuccess ->
            createUserResult.value = isSuccess
        }
    }
}
