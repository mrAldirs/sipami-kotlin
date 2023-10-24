package com.example.sipami.views.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.databinding.CLoginBinding
import com.example.sipami.views.layout._actvMain

class _actvLogin : AppCompatActivity() {
    private lateinit var _b: CLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CLoginBinding.inflate(layoutInflater)
        setContentView(_b.root)

        _b.btnMasuk.setOnClickListener {
            startActivity(Intent(this@_actvLogin, _actvMain::class.java))
        }

        _b.btnAkun.setOnClickListener {
            startActivity(Intent(this@_actvLogin, _actvRegis::class.java))
        }
    }
}