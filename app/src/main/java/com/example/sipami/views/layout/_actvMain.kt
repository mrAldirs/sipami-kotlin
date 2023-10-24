package com.example.sipami.views.layout

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.sipami.R
import com.example.sipami.databinding.CDashboardBinding
import com.example.sipami.databinding.ContentDashboardBinding
import com.example.sipami.views.history._fragHistory
import com.example.sipami.views.profil._fragProfil
import com.example.sipami.views.surat._actvSurat
import com.google.android.material.bottomnavigation.BottomNavigationView

class _actvMain : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var _b: CDashboardBinding
    private lateinit var _b_content: ContentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CDashboardBinding.inflate(layoutInflater)
        _b_content = _b.content
        setContentView(_b.root)

        _b_content.btnSurat.setOnClickListener {
            startActivity(Intent(this@_actvMain, _actvSurat::class.java))
        }

        _b.bottomNavigasi.setOnNavigationItemSelectedListener(this)
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