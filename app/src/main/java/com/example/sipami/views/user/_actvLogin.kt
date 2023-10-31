package com.example.sipami.views.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.api.global.Config
import com.example.sipami.databinding.CLoginBinding
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.SharedPreferences
import com.example.sipami.utils.helper.Toast
import com.google.firebase.auth.FirebaseUser

class _actvLogin : AppCompatActivity(), IntentHelper {
    private lateinit var _b: CLoginBinding
    private lateinit var preferences: SharedPreferences
    private val firebase = Config.firestore
    private val auth = Config.firebaseAuth
    var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CLoginBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.hide()
        Toast.init(applicationContext)
        preferences = SharedPreferences(this)

        validasi()
        registrasi()
    }

    private fun registrasi() {
        _b.btnAkun.setOnClickListener {
            intentActivity(regis())
        }
    }

    private fun validasi() {
        _b.btnMasuk.setOnClickListener {
            if (!_b.txUsername.text.toString().equals("") && !_b.txPassword.text.toString().equals("")) {
                login()
            } else {
                Toast.message("Email dan Password harus diisi!")
            }
        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(_b.txUsername.text.toString(),_b.txPassword.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    currentUser = auth.currentUser
                    if (currentUser!=null){
                        if (currentUser!!.isEmailVerified){
                            dataAkun()
                        }else{
                            Toast.message("Email anda belum terverifikasi")
                        }
                    }
                }else{
                    Toast.message("Username / Password salah")
                }
            }
    }

    private fun dataAkun() {
        firebase.collection("user")
            .whereEqualTo("email", _b.txUsername.text.toString())
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0]
                    val st1 = document.get("id").toString()
                    preferences.saveString("id", st1)

                    intentActivity(actionLogin())
                    finishAffinity()
                }
            }
    }
}