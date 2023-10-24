package com.example.sipami.views.surat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.databinding.CSuratBinding

class _actvSurat : AppCompatActivity() {
    private lateinit var _b: CSuratBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CSuratBinding.inflate(layoutInflater)
        setContentView(_b.root)
    }
}