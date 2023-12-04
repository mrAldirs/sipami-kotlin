package com.example.sipami.views.admin.surat

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sipami.R
import com.example.sipami.adapter.AdpHistoryAdm
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.AdmCSuratBinding
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.Toast

class _actvSurat : AppCompatActivity(), IntentHelper {

    private lateinit var _b: AdmCSuratBinding
    private lateinit var vmSurat : Surat
    private lateinit var adapter: AdpHistoryAdm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = AdmCSuratBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("Surat Masuk")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vmSurat = ViewModelProvider(this).get(Surat::class.java)
    }

    fun show(id: String) {
        intentActivity(suratShowAdmin(id))
    }

    private fun listItem() {
        adapter = AdpHistoryAdm(ArrayList(), this)
        _b.recyclerView.layoutManager = LinearLayoutManager(this)
        _b.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        loadAll()
    }

    private fun loadAll() {
        listItem()
        vmSurat.loadAll().observe(this, Observer { history ->
            adapter.setData(history)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun delete(id: String) {
        AlertDialog.Builder(this)
            .setTitle("Hapus!")
            .setIcon(android.R.drawable.stat_sys_warning)
            .setMessage("Apakah Anda ingin menghapus history ini?")
            .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                vmSurat.delete(id).observe(this, Observer { result ->
                    loadAll()
                    Toast.message("Berhasil menghapus data!")
                })
            })
            .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
            })
            .show()
    }
}