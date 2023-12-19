package com.example.sipami.api.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sipami.api.repository.__surat___
import com.example.sipami.models.mKategori
import com.example.sipami.models.mSurat

class Surat : ViewModel() {
    private val __surat = __surat___()

    fun getChart() : LiveData<List<mSurat.__mChartModel>> {
        return __surat.getChart()
    }

    fun getKategori(callback: (List<mKategori>?, Exception?) -> Unit) {
        __surat.getKategoriList(callback)
    }

    fun insertSurat(surat: mSurat.__mSurat): LiveData<Boolean> {
        return __surat.insertSurat(surat)
    }

    fun loadData(user_id: String): LiveData<List<mSurat.__mHistory>>  {
        return __surat.loadData(user_id)
    }

    fun delete(id: String): LiveData<Boolean>  {
        return __surat.delete(id)
    }

    fun show(id: String): LiveData<mSurat.__mSuratAll> {
        return __surat.show(id)
    }

    fun showKategori(id: String): LiveData<mKategori> {
        return __surat.showKategori(id)
    }

    fun loadKategori(): LiveData<List<mKategori>> {
        return __surat.loadKategori()
    }

    fun deleteKategori(id: String): LiveData<Boolean> {
        return __surat.deleteKategori(id)
    }

    fun insertKategori(kategori: mKategori): LiveData<Boolean> {
        return __surat.insertKategori(kategori)
    }

    fun loadAll(status: String): LiveData<List<mSurat.__mHistory>> {
        return __surat.loadAll(status)
    }

    fun editSurat(surat: mSurat.__mSurat): LiveData<Boolean> {
        return __surat.editSurat(surat)
    }

    fun uploadFile(id: String, nos: String, uri: Uri): LiveData<Boolean> {
        return __surat.uploadFile(id, nos, uri)
    }

    fun loadFile(filter: String? = null): LiveData<List<mSurat.__mFile>> {
        return __surat.loadFile(filter)
    }
}