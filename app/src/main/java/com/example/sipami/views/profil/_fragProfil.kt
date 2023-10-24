package com.example.sipami.views.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sipami.databinding.FProfilBinding

class _fragProfil : Fragment() {
    private lateinit var _b : FProfilBinding
    lateinit var _v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _b = FProfilBinding.inflate(layoutInflater)
        _v = _b.root

        _b.btnEdit.setOnClickListener {
            startActivity(Intent(_v.context, _actvProfilEdt::class.java))
        }

        return _v
    }
}