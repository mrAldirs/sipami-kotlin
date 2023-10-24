package com.example.sipami.views.surat

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.databinding.CSuratBinding
import com.example.sipami.databinding.FormSuratBinding
import com.example.sipami.utils.helper.DatePickerHelper
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.SharedPreferences
import com.squareup.picasso.Picasso

class _actvSurat : AppCompatActivity(), IntentHelper {
    private lateinit var _b: CSuratBinding
    private lateinit var _b_content: FormSuratBinding
    private lateinit var vmProfil: Profil
    private lateinit var preferences: SharedPreferences
    private lateinit var datePickerHelper: DatePickerHelper

    val semester = arrayOf("Pilih Semester","1","2","3","4","5","6")
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CSuratBinding.inflate(layoutInflater)
        _b_content = _b.content
        setContentView(_b.root)
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        preferences = SharedPreferences(this@_actvSurat)
        datePickerHelper = DatePickerHelper(this)

        date()
        semester()
    }

    private fun semester() {
        adapter = ArrayAdapter(this@_actvSurat, android.R.layout.simple_list_item_1, semester)
        _b_content.insSemester.adapter = adapter
    }

    private fun date() {
        _b_content.insTanggal.setOnClickListener {
            datePickerHelper.showDatePickerDialog(_b_content.insTanggal)
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        vmProfil.profil(preferences.getString("id", "")).observe(this@_actvSurat, Observer {
            _b_content.insNama.setText(it.nama)
            _b_content.insNim.setText(it.nim)
            if (it.alamat.equals("null")) { _b_content.insAlamat.setText("<None>") } else { _b_content.insAlamat.setText(it.alamat) }
        })
    }
}