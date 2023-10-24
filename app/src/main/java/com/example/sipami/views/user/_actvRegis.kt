package com.example.sipami.views.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.databinding.CRegisBinding

class _actvRegis : AppCompatActivity() {
    private lateinit var _b: CRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CRegisBinding.inflate(layoutInflater)
        setContentView(_b.root)
    }
}