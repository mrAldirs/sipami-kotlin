package com.example.sipami.api.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sipami.api.global.Config
import com.example.sipami.api.global.Data
import com.example.sipami.models.mSurat
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class __surat___ {
    private val firestore = Config.firestore

    fun getKategoriList(callback: (List<mSurat.__mKategori>?, Exception?) -> Unit) {
        firestore.collection(Data.kategori).get()
            .addOnSuccessListener { querySnapshot ->
                val kategoriList = mutableListOf<mSurat.__mKategori>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val nama = document.getString("nama") ?: ""
                    kategoriList.add(mSurat.__mKategori(id, nama))
                }
                callback(kategoriList, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception)
            }
    }

    fun insertSurat(surat: mSurat.__mSurat): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,String>()
        hm.put("id", surat.id)
        hm.set("kategori_id", surat.kategori_id)
        hm.set("user_id", surat.user_id)
        hm.set("tanggal", surat.tanggal)
        hm.set("semester", surat.semester)
        hm.set("alasan", surat.alasan)
        hm.set("status", surat.status)
        hm.set("file", "")

        firestore.collection(Data.surat)
            .document(surat.id)
            .set(hm)
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }

        return result
    }

    fun loadData(user_id: String): LiveData<List<mSurat.__mHistory>> {
        val resultLiveData = MutableLiveData<List<mSurat.__mHistory>>()

        firestore.collection(Data.surat)
            .whereEqualTo("user_id", user_id)
            .get()
            .addOnSuccessListener { result ->
                val dataList = mutableListOf<mSurat.__mHistory>()
                for (doc in result) {
                    val data = mSurat.__mHistory(
                        doc.get("id").toString(),doc.get("tanggal").toString(),doc.get("status").toString()
                    )
                    dataList.add(data)
                }
                resultLiveData.value = dataList
            }
            .addOnFailureListener { exception ->

            }
        return resultLiveData
    }

    fun delete(id: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        firestore.collection(Data.surat)
            .document(id)
            .delete()
            .addOnSuccessListener {
                resultLiveData.value = true
            }
            .addOnFailureListener {
                resultLiveData.value = false
            }

        return resultLiveData
    }

    fun show(id: String): LiveData<mSurat.__mSurat> {
        val resultLiveData = MutableLiveData<mSurat.__mSurat>()

        firestore.collection(Data.surat)
            .document(id)
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val data = mSurat.__mSurat(
                        doc.get("id").toString(),
                        doc.get("kategori_id").toString(), "",
                        doc.get("tanggal").toString(),
                        doc.get("semester").toString(),
                        doc.get("alasan").toString(),
                        doc.get("status").toString(),
                        doc.get("file").toString()
                    )
                    resultLiveData.value = data
                }
            }
            .addOnFailureListener { exception ->

            }

        return resultLiveData
    }

    fun showKategori(id: String): LiveData<mSurat.__mKategori> {
        val resultLiveData = MutableLiveData<mSurat.__mKategori>()

        firestore.collection(Data.surat)
            .document(id)
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val data = mSurat.__mKategori(
                        doc.get("id").toString(),
                        doc.get("nama").toString()
                    )
                    resultLiveData.value = data
                }
            }
            .addOnFailureListener { exception ->

            }

        return resultLiveData
    }
}