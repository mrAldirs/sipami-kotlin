package com.example.sipami.api.repository

import com.example.sipami.api.global.Config
import com.google.firebase.auth.UserProfileChangeRequest

class __user___ {
    val auth = Config.firebaseAuth
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Update user profile with display name
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .build()
                        user.updateProfile(profileUpdates)

                        // Send email verification
                        user.sendEmailVerification()

                        onComplete(true)
                    } else {
                        onComplete(false)
                    }
                } else {
                    onComplete(false)
                }
            }
    }
}