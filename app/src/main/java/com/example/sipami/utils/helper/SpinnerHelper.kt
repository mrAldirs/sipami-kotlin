package com.example.sipami.utils.helper

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

class SpinnerHelper(private val context: Context) {

    private val prodiList: List<String> = listOf("--Pilih--","D-III Akuntansi","D-III Manajemen Informatika","D-III Teknik Mesin","D-IV Keuangan","D-IV Teknik Elektro","D-IV Teknik Mesin Produksi dan Perawatan")
    private val semesterList: List<String> = listOf("--Pilih--","1 (Satu)","2 (Dua)","3 (Tiga)","4 (Empat)","5 (Lima)","6 (Enam)","7 (Tujuh)","8 (Delapan)","Lulus")
    private val layananList: List<String> = listOf("--Pilih--","Surat Keterangan Aktif","Surat Keterangan Selesai Ujian T.A.","Surat Permohonan Prakerin","Surat Keterangan Pendamping SKL")

    fun prodi(spinner: Spinner) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, prodiList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun semester(spinner: Spinner) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, semesterList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun layanan(spinner: Spinner) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, layananList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun getSelectedItem(spinner: Spinner): String {
        return spinner.selectedItem.toString()
    }
}
