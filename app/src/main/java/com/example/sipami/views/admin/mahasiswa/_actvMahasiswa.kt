package com.example.sipami.views.admin.mahasiswa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sipami.adapter.AdpMahasiswa
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.databinding.AdmCMahasiswaBinding

class _actvMahasiswa : AppCompatActivity() {
    private lateinit var _b: AdmCMahasiswaBinding
    private lateinit var vmProfil: Profil
    private lateinit var adapter: AdpMahasiswa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = AdmCMahasiswaBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.title = "Mahasiswa"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vmProfil = ViewModelProvider(this).get(Profil::class.java)

        adapter = AdpMahasiswa(listOf())
        _b.rvMahasiswa.layoutManager = LinearLayoutManager(this)
        _b.rvMahasiswa.adapter = adapter
        vmProfil.loadMahasiswa().observe(this, Observer { data ->
            adapter.setData(data)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}