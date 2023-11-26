package com.example.sipami.views.setelahLogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.databinding.CSetelahLoginBinding

class _actvMahasiswa : AppCompatActivity() {

    private lateinit var _b: CSetelahLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CSetelahLoginBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.hide()

        _b.textView.text = "Anda berhasil login sebagai mahasiswa"
    }
}