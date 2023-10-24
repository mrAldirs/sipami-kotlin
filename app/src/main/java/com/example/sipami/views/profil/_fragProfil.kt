package com.example.sipami.views.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.databinding.FProfilBinding
import com.example.sipami.utils.helper.SharedPreferences
import com.squareup.picasso.Picasso

class _fragProfil : Fragment() {
    private lateinit var _b : FProfilBinding
    lateinit var _v: View
    private lateinit var vmProfil: Profil
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _b = FProfilBinding.inflate(layoutInflater)
        _v = _b.root
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        preferences = SharedPreferences(_v.context)

        _b.btnEdit.setOnClickListener {
            startActivity(Intent(_v.context, _actvProfilEdt::class.java))
        }

        return _v
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        vmProfil.profil(preferences.getString("id", "")).observe(this@_fragProfil, Observer {
            _b.tvNama.setText(it.nama)
            _b.tvNim.setText(it.nim)
            if (it.prodi.equals("null")) { _b.tvProdi.setText("<None>") } else { _b.tvProdi.setText(it.prodi) }
            if (it.telpon.equals("null")) { _b.tvTelpon.setText("<None>") } else { _b.tvTelpon.setText(it.telpon) }
            if (it.alamat.equals("null")) { _b.tvAlamat.setText("<None>") } else { _b.tvAlamat.setText(it.alamat) }
            Picasso.get().load(it.foto).into(_b.imgFoto)
        })
    }
}