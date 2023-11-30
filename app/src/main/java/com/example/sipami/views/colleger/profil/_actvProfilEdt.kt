package com.example.sipami.views.colleger.profil

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.R
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.databinding.CProfilEdtBinding
import com.example.sipami.databinding.FormProfilBinding
import com.example.sipami.models.mProfil
import com.example.sipami.utils.helper.SharedPreferences
import com.example.sipami.utils.helper.Toast
import com.squareup.picasso.Picasso

class _actvProfilEdt : AppCompatActivity() {
    private lateinit var _b: CProfilEdtBinding
    private lateinit var _b_content: FormProfilBinding
    private lateinit var vmProfil: Profil
    private lateinit var preferences: SharedPreferences

    val RC_OK = 100
    lateinit var uri: Uri

    val prodi = arrayOf("Pilih Salah Satu","D3 Manajemen Informatika","D3 Teknik Mesin","D3 Akuntansi","D4 Teknik Elektro","D4 Teknik Mesin Perwatan & Produksi","D4 Keuangan")
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CProfilEdtBinding.inflate(layoutInflater)
        _b_content = _b.content
        setContentView(_b.root)
        supportActionBar?.setTitle("Edit Profil")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Toast.init(this@_actvProfilEdt)
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        preferences = SharedPreferences(this@_actvProfilEdt)

        prodi()
        setImage()
        loadData()
        uri = Uri.EMPTY
        submit()
    }

    private fun setImage() {
        _b_content.btnChoose.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setType("image/*")
            startActivityForResult(intent, RC_OK)
        }
    }

    private fun prodi() {
        adapter = ArrayAdapter(this@_actvProfilEdt, android.R.layout.simple_list_item_1, prodi)
        _b_content.insProdi.adapter = adapter
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_OK) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data?.data!!
                Log.e("url-foto", uri.toString())
                Picasso.get().load(uri.toString()).into(_b_content.insImg)
            }
        }
    }

    private fun loadData() {
        vmProfil.profil(preferences.getString("id", "")).observe(this@_actvProfilEdt, Observer {
            _b_content.insNama.setText(it.nama)
            _b_content.insNim.setText(it.nim)
            if (!it.prodi.equals("null")) { _b_content.insProdi.setSelection(adapter.getPosition(it.prodi)) }
            if (it.telpon.equals("null")) { _b_content.insTelpon.setText("<None>") } else { _b_content.insTelpon.setText(it.telpon) }
            if (it.alamat.equals("null")) { _b_content.insAlamat.setText("<None>") } else { _b_content.insAlamat.setText(it.alamat) }
            Picasso.get().load(it.foto).into(_b_content.insImg)
        })
    }

    private fun validasi(uri: Uri?) {
        val data = mProfil(
            "",preferences.getString("id", ""),
            _b_content.insNama.text.toString(),
            _b_content.insProdi.selectedItem.toString(),
            _b_content.insNim.text.toString(),
            _b_content.insTelpon.text.toString(),
            _b_content.insAlamat.text.toString(),""
        )

        vmProfil.edit(data, uri)
            .observe(this@_actvProfilEdt, Observer { success ->
                Toast.message("Berhasil mengubah data!")
                onBackPressed()
            })
    }

    private fun submit() {
        _b.btnSubmit.setOnClickListener { AlertDialog.Builder(this)
            .setTitle("Informasi!")
            .setIcon(android.R.drawable.stat_sys_warning)
            .setMessage("Apakah Anda ingin mengedit profil Anda?")
            .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                validasi(uri)
            })
            .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->

            })
            .show()
        }
    }
}