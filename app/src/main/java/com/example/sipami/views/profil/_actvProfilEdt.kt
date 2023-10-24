package com.example.sipami.views.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.databinding.CProfilEdtBinding

class _actvProfilEdt : AppCompatActivity() {
    private lateinit var _b: CProfilEdtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CProfilEdtBinding.inflate(layoutInflater)
        setContentView(_b.root)
    }
}