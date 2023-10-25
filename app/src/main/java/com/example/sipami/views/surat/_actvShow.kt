package com.example.sipami.views.surat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.R
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.CSuratShowBinding
import com.example.sipami.utils.helper.SharedPreferences
import com.squareup.picasso.Picasso

class _actvShow : AppCompatActivity() {
    private lateinit var _b: CSuratShowBinding
    private lateinit var vmProfil: Profil
    private lateinit var vmSurat: Surat
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CSuratShowBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("Detail Pengajuan Surat")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        vmSurat = ViewModelProvider(this).get(Surat::class.java)
        preferences = SharedPreferences(this@_actvShow)

        show()
        profil()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }

    private fun profil() {
        vmProfil.profil(preferences.getString("id", "")).observe(this@_actvShow, Observer { data ->
            _b.dtNama.text = data.nama
            _b.dtNim.text = data.nim
            _b.dtAlamat.text = data.alamat
            _b.dtProdi.text = data.prodi
            Picasso.get().load(data.foto).into(_b.imgProfil)
        })
    }

    private fun show() {
        vmSurat.show(intent.getStringExtra("id").toString()).observe(this@_actvShow, Observer { data ->
            _b.dtSemester.text = data.semester
            _b.dtTgl.text = data.tanggal
            _b.dtStatus.text = data.status
            if (data.equals("On Process")) {
                _b.icStatus.setBackgroundResource(R.drawable.ic_proses)
            }
            var kategoriId = data.kategori_id

            vmSurat.showKategori(kategoriId).observe(this@_actvShow, Observer { _data ->
                _b.dtKeperluan.text = _data.nama
            })
        })
    }
}