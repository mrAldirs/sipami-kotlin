package com.example.sipami.api.global

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

object Config {
    @SuppressLint("StaticFieldLeak")
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
}