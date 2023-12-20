package com.example.sipami.api.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sipami.api.global.Config
import com.example.sipami.api.global.Data
import com.example.sipami.models.mKategori
import com.example.sipami.models.mSurat
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class __surat___ {
    private val firestore = Config.firestore

    fun getChart() : LiveData<List<mSurat.__mChartModel>> {
        val resultLiveData = MutableLiveData<List<mSurat.__mChartModel>>()

        firestore.collection(Data.mahasiswa)
            .get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<mSurat.__mChartModel>()
                for (doc in docs) {
                    val id = doc.get("user_id").toString()
                    val name = doc.get("nama").toString()

                    firestore.collection(Data.surat)
                        .whereEqualTo("user_id", id)
                        .get()
                        .addOnSuccessListener { sizeResult ->
                            val total = sizeResult.size()

                            val data = mSurat.__mChartModel(name, total.toLong())
                            dataList.add(data)

                            val sortedDataList = dataList.sortedByDescending { it.count }.take(5)
                            resultLiveData.value = sortedDataList
                        }
                }
            }

        return resultLiveData
    }

    fun loadKategori() : LiveData<List<mKategori>> {
        val resultLiveData = MutableLiveData<List<mKategori>>()

        firestore.collection(Data.kategori)
            .get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<mKategori>()
                for (doc in docs) {
                    val data = mKategori(
                        doc.getString("id").toString(),
                        doc.getString("nama").toString()
                    )
                    dataList.add(data)
                }
                resultLiveData.value = dataList
            }

        return resultLiveData
    }

    fun deleteKg(id: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        firestore.collection(Data.kategori)
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

    fun insertKategori(kategori: mKategori) : LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        val hm = HashMap<String,Any>()
        hm.set("id", kategori.id)
        hm.set("nama", kategori.nama)

        firestore.collection(Data.kategori)
            .document(kategori.id)
            .set(hm)
            .addOnSuccessListener {
                resultLiveData.value = true
            }
            .addOnFailureListener {
                resultLiveData.value = false
            }

        return resultLiveData
    }

    fun getKategoriList(callback: (List<mKategori>?, Exception?) -> Unit) {
        firestore.collection(Data.kategori).get()
            .addOnSuccessListener { querySnapshot ->
                val kategoriList = mutableListOf<mKategori>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val nama = document.getString("nama") ?: ""
                    kategoriList.add(mKategori(id, nama))
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
        hm.set("nama_surat", "")
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

    fun editSurat(surat: mSurat.__mSurat): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,Any>()
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
            .update(hm)
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

    fun loadAll(status: String): LiveData<List<mSurat.__mHistory>> {
        val resultLiveData = MutableLiveData<List<mSurat.__mHistory>>()

        firestore.collection(Data.surat)
            .whereEqualTo("status", status)
            .get()
            .addOnSuccessListener { result ->
                val dataList = mutableListOf<mSurat.__mHistory>()
                for (doc in result) {
                    val data = mSurat.__mHistory(
                        doc.getString("id") ?: "",
                        doc.getString("tanggal") ?: "",
                        doc.getString("status") ?: ""
                    )
                    dataList.add(data)
                }
                resultLiveData.value = dataList
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }

        return resultLiveData
    }

    fun loadFile(filter: String?): LiveData<List<mSurat.__mFile>> {
        val resultLiveData = MutableLiveData<List<mSurat.__mFile>>()

        val query = if (filter != null) {
            firestore.collection(Data.kategori)
                .whereEqualTo("nama", filter)
        } else {
            firestore.collection(Data.kategori)
        }

        query.get()
            .addOnSuccessListener { result ->
                val dataList = mutableListOf<mSurat.__mFile>()
                for (doc in result) {
                    val id = doc.get("id").toString()
                    val nama = doc.get("nama").toString()

                    firestore.collection(Data.surat)
                        .whereEqualTo("kategori_id", id)
                        .get()
                        .addOnSuccessListener {
                            for (doc in it) {
                                val data = mSurat.__mFile(
                                    doc.get("id").toString(),
                                    nama,
                                    doc.get("nomor_surat").toString(),
                                    doc.get("file").toString()
                                )
                                dataList.add(data)
                            }
                            resultLiveData.value = dataList
                        }
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

    fun show(id: String): LiveData<mSurat.__mSuratAll> {
        val resultLiveData = MutableLiveData<mSurat.__mSuratAll>()

        firestore.collection(Data.surat)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs)  {
                    val id = doc.get("id").toString()
                    val kategori_id = doc.get("kategori_id").toString()
                    val user_id = doc.get("user_id").toString()
                    val tanggal = doc.get("tanggal").toString()
                    val semester = doc.get("semester").toString()
                    val alasan = doc.get("alasan").toString()
                    val status = doc.get("status").toString()
                    val file = doc.get("file").toString()

                    firestore.collection(Data.kategori)
                        .whereEqualTo("id", kategori_id)
                        .get()
                        .addOnSuccessListener {
                            for (doc in it) {
                                val data = mSurat.__mSuratAll(
                                    id,
                                    kategori_id,
                                    doc.get("nama").toString(),
                                    user_id,
                                    tanggal,
                                    semester,
                                    alasan,
                                    status,
                                    file
                                )
                                resultLiveData.value = data
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->

            }

        return resultLiveData
    }

    fun showKategori(id: String): LiveData<mKategori> {
        val resultLiveData = MutableLiveData<mKategori>()

        firestore.collection(Data.surat)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val data = mKategori(
                        doc.getString("id").toString(),
                        doc.getString("nama").toString()
                    )
                    resultLiveData.value = data
                    break
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure if needed
            }

        return resultLiveData
    }

    fun uploadFile(id: String, nos: String, uri: Uri): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        val hm = HashMap<String, Any>()
        hm.set("id", id)
        hm.set("nomor_surat", nos)
        hm.set("status", "On Finished")

        if (uri != Uri.EMPTY) {
            val fileName = "PDF" + SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())
            val fileRef = FirebaseStorage.getInstance().reference.child(fileName + ".pdf")
            val uploadTask = uri?.let { fileRef.putFile(it) }

            uploadTask!!.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                fileRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hm.put("file", task.result.toString())

                    firestore.collection(Data.surat)
                        .document(id)
                        .update(hm)
                        .addOnSuccessListener { resultLiveData.value = true }
                        .addOnFailureListener { resultLiveData.value = false }
                } else {
                    resultLiveData.value = false
                }
            }
        } else {
            firestore.collection(Data.surat)
                .document()
                .set(hm)
                .addOnSuccessListener { resultLiveData.value = true }
                .addOnFailureListener { resultLiveData.value = false }
        }

        return resultLiveData
    }
}