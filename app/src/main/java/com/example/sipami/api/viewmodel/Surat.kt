package com.example.sipami.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sipami.api.repository.__surat___
import com.example.sipami.models.mKategori
import com.example.sipami.models.mSurat

class Surat : ViewModel() {
    private val __surat = __surat___()

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

    fun show(id: String): LiveData<mSurat.__mSurat> {
        return __surat.show(id)
    }

    fun showKategori(id: String): LiveData<mKategori> {
        return __surat.showKategori(id)
    }

    fun loadAll(): LiveData<List<mSurat.__mHistory>> {
        return __surat.loadAll()
    }

    fun editSurat(surat: mSurat.__mSurat): LiveData<Boolean> {
        return __surat.editSurat(surat)
    }
}