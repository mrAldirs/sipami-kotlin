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
import com.example.sipami.adapter.AdpSuratAdm
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.AdmCSuratBinding
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.Toast

class _actvHistory : AppCompatActivity(), IntentHelper {

    private lateinit var _b: AdmCSuratBinding
    private lateinit var vmSurat : Surat
    private lateinit var adapter: AdpHistoryAdm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = AdmCSuratBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("History")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vmSurat = ViewModelProvider(this).get(Surat::class.java)
        loadAll()
    }

    fun show(id: String) {
        intentActivity(suratShowAdmin(id))
    }

    private fun listItem() {
        adapter = AdpHistoryAdm(ArrayList(), this)
        _b.recyclerView.layoutManager = LinearLayoutManager(this)
        _b.recyclerView.adapter = adapter
    }

    private fun loadAll() {
        listItem()
        vmSurat.loadAll("On Finished").observe(this, Observer { history ->
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
}