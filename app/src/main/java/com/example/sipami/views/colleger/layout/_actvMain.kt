package com.example.sipami.views.colleger.layout

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.R
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.databinding.CDashboardBinding
import com.example.sipami.databinding.ContentDashboardBinding
import com.example.sipami.databinding.HeadDashboardBinding
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.SharedPreferences
import com.example.sipami.utils.helper.Toast
import com.example.sipami.views.colleger.history._fragHistory
import com.example.sipami.views.colleger.profil._fragProfil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.picasso.Picasso


class _actvMain : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, IntentHelper {
    private lateinit var _b: CDashboardBinding
    private lateinit var _b_header: HeadDashboardBinding
    private lateinit var _b_content: ContentDashboardBinding
    private lateinit var vmProfil: Profil
    private lateinit var preferences: SharedPreferences
    private var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    var userProdi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CDashboardBinding.inflate(layoutInflater)
        _b_content = _b.content
        _b_header = _b.header
        setContentView(_b.root)
        supportActionBar?.setTitle("Dashboard")
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        preferences = SharedPreferences(this)
        Toast.init(applicationContext)

        formAction()
        loginAction()
        kegiatan()
        notelpon()
        matkul()

        _b.bottomNavigasi.setOnNavigationItemSelectedListener(this)
    }

    private fun matkul() {
        _b_content.btnJadwal.setOnClickListener {
            intentActivity(jadwal())
        }
    }

    private fun notelpon() {
        _b_content.btnKontak.setOnClickListener {
            intentActivity(notelp())
        }
    }

    private fun kegiatan() {
        _b_content.btnKegiatan.setOnClickListener {
            intentActivity(himaAction())
        }
    }

    private fun loginAction() {
        remoteConfig.setDefaultsAsync(R.xml.remote_config_login)
        val allowedLogin = remoteConfig.getString("login_access")

        var userId = preferences.getString("id", "")
        if (allowedLogin == userId) {
            _b.cDashboard.setBackgroundColor(Color.parseColor("#DD9F9F"))
            _b_header.cdHead.setCardBackgroundColor(Color.parseColor("#E49C97"))
        } else {
            null
        }
    }

    private fun formAction() {
        _b_content.btnSurat.setOnClickListener {
            remoteConfig.setDefaultsAsync(R.xml.remote_config_prodi)

            val allowedProdi = remoteConfig.getString("prodi_access")

            if (userProdi == allowedProdi) {
                intentActivity(form())
            } else {
                Toast.message("Hanya prodi D3 Manajemen Informatika dapat akses!")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu._mn_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mn_option -> {
                val popupMenu = PopupMenu(this, findViewById(R.id.mn_option))
                popupMenu.menuInflater.inflate(R.menu._mn_logout, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { popupItem ->
                    when (popupItem.itemId) {
                        R.id.mn_logout -> {
                            preferences.remove("id")
                            intentActivity(actionLogout())
                            finishAffinity()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }

                popupMenu.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
         vmProfil.profil(preferences.getString("id", "")).observe(this@_actvMain, Observer {
            _b_header.tvNama.setText(it.nama)
            _b_header.tvNim.setText(it.nim)
            userProdi = it.prodi
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