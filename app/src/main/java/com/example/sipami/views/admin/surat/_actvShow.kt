package com.example.sipami.views.admin.surat

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.R
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.AdmCSuratShowBinding
import com.example.sipami.utils.helper.IntentHelper
import com.squareup.picasso.Picasso

class _actvShow : AppCompatActivity(), IntentHelper {
    private lateinit var _b: AdmCSuratShowBinding
    private lateinit var vmProfil: Profil
    private lateinit var vmSurat: Surat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = AdmCSuratShowBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("Detail Pengajuan Surat")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        vmSurat = ViewModelProvider(this).get(Surat::class.java)

        accept()
    }

    override fun onStart() {
        super.onStart()
        show()
    }

    private fun accept() {
        _b.btnAccept.setOnClickListener {
            var frag = _fragAcc()

            val bundle = Bundle()
            bundle.putString("id", intent.getStringExtra("id").toString())
            frag.arguments = bundle

            frag.show(supportFragmentManager, "")
        }
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

    private fun show() {
        vmSurat.show(intent.getStringExtra("id").toString()).observe(this@_actvShow, Observer { data ->
            _b.dtSemester.text = data.semester
            _b.dtTgl.text = data.tanggal
            _b.dtStatus.text = data.status
            _b.dtAlasan.text = data.alasan

            vmProfil.profilById(data.user_id).observe(this@_actvShow, Observer { data ->
                _b.dtNama.text = data.nama
                _b.dtNim.text = data.nim
                _b.dtAlamat.text = data.alamat
                _b.dtProdi.text = data.prodi
                Picasso.get().load(data.foto).into(_b.imgProfil)
            })

            if (data.status.equals("On Process")) {
                _b.icStatus.setBackgroundResource(R.drawable.ic_proses)
                _b.icDownload.setBackgroundResource(R.drawable.ic_box)
                _b.txtDownload.text = "File Belum Tersedia"
                _b.btnAccept.visibility = View.VISIBLE
            } else {
                _b.icStatus.setBackgroundResource(R.drawable.ic_done)
                _b.icDownload.setBackgroundResource(R.drawable.ic_pdf)
                _b.txtDownload.text = "Lihat File PDF"
                _b.btnAccept.visibility = View.GONE
                _b.btnDownload.setOnClickListener {
                    val pdf = data.file.toUri()
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(pdf, "application/pdf")
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                }
            }
            _b.dtKeperluan.text = data.kategori_name
        })
    }
}