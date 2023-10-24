package com.example.sipami.views.layout

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.R
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.databinding.CDashboardBinding
import com.example.sipami.databinding.ContentDashboardBinding
import com.example.sipami.databinding.HeadDashboardBinding
import com.example.sipami.utils.helper.SharedPreferences
import com.example.sipami.utils.helper.Toast
import com.example.sipami.views.history._fragHistory
import com.example.sipami.views.profil._fragProfil
import com.example.sipami.views.surat._actvSurat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class _actvMain : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var _b: CDashboardBinding
    private lateinit var _b_header: HeadDashboardBinding
    private lateinit var _b_content: ContentDashboardBinding
    private lateinit var vmProfil: Profil
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CDashboardBinding.inflate(layoutInflater)
        _b_content = _b.content
        _b_header = _b.header
        setContentView(_b.root)
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        preferences = SharedPreferences(this)
        Toast.init(applicationContext)

        _b_content.btnSurat.setOnClickListener {
            startActivity(Intent(this@_actvMain, _actvSurat::class.java))
        }

        _b.bottomNavigasi.setOnNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
         vmProfil.profil(preferences.getString("id", "")).observe(this@_actvMain, Observer {
            _b_header.tvNama.setText(it.nama)
            _b_header.tvNim.setText(it.nim)
            if (it.prodi.equals("null")) {
                _b_header.tvJurusan.visibility = View.GONE
            } else {
                _b_header.tvJurusan.setText(it.prodi)
            }
            Picasso.get().load(it.foto).into(_b_header.imgFoto)
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.nav_home -> {
                supportActionBar?.setTitle("Dashboard")
                _b.frameLayout.visibility = View.GONE
            }
            R.id.nav_history -> {
                supportActionBar?.setTitle("Activity Riwayat")
                var frag = _fragHistory()

                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, frag).commit()
                _b.frameLayout.setBackgroundColor(Color.argb(255,255,255,255))
                _b.frameLayout.visibility = View.VISIBLE
            }
            R.id.nav_profil -> {
                supportActionBar?.setTitle("Profil Mahasiswa")
                var frag = _fragProfil()

                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, frag).commit()
                _b.frameLayout.setBackgroundColor(Color.argb(255,255,255,255))
                _b.frameLayout.visibility = View.VISIBLE
            }
        }
        return true
    }
}