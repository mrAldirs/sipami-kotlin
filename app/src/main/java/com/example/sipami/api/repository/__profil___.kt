package com.example.sipami.api.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sipami.api.global.Config
import com.example.sipami.api.global.Data
import com.example.sipami.models.mMahasiswa
import com.example.sipami.models.mProfil
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class __profil___ {
    val fr = Config.firestore
    val profil = Data.mahasiswa

    fun getProfil(user_id: String): LiveData<mProfil> {
        val resultLiveData = MutableLiveData<mProfil>()

        fr.collection(profil)
            .document(user_id)
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val data = mProfil(
                        doc.get("id").toString(),
                        doc.get("user_id").toString(),
                        doc.get("nama").toString(),
                        doc.get("prodi").toString(),
                        doc.get("nim").toString(),
                        doc.get("telpon").toString(),
                        doc.get("alamat").toString(),
                        doc.get("foto").toString()
                    )
                    resultLiveData.value = data
                }
            }
            .addOnFailureListener { exception ->

            }

        return resultLiveData
    }

    fun getProfilById(user_id: String): LiveData<mProfil> {
        val resultLiveData = MutableLiveData<mProfil>()

        fr.collection(profil)
            .whereEqualTo("user_id", user_id)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val data = mProfil(
                        doc.getString("id").toString(),
                        doc.getString("user_id").toString(),
                        doc.getString("nama").toString(),
                        doc.getString("prodi").toString(),
                        doc.getString("nim").toString(),
                        doc.getString("telpon").toString(),
                        doc.getString("alamat").toString(),
                        doc.getString("foto").toString()
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

    fun loadMahasiswa() : LiveData<List<mMahasiswa>> {
        val resultLiveData = MutableLiveData<List<mMahasiswa>>()

        fr.collection(profil)
            .get()
            .addOnSuccessListener { documents ->
                val dataList = mutableListOf<mMahasiswa>()
                for (doc in documents) {
                    val data = mMahasiswa(
                        doc.getString("id").toString(),
                        doc.getString("nama").toString(),
                        doc.getString("nim").toString(),
                        doc.getString("foto").toString()
                    )
                    dataList.add(data)
                }
                resultLiveData.value = dataList
            }
            .addOnFailureListener { exception ->
            }

        return resultLiveData
    }

    fun edit(profil: mProfil, uri: Uri?): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        val hm = HashMap<String, Any>()
        hm.set("nama", profil.nama)
        hm.set("prodi", profil.prodi)
        hm.set("nim", profil.nim)
        hm.set("telpon", profil.telpon)
        hm.set("alamat", profil.alamat)
        hm.set("updated_at", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))

        if (uri != Uri.EMPTY) {
            val fileName = "IMG" + SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())
            val fileRef = FirebaseStorage.getInstance().reference.child(fileName + ".jpg")
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
                    hm.put("foto", task.result.toString())

                    fr.collection(Data.mahasiswa)
                        .document(profil.userId)
                        .update(hm)
                        .addOnSuccessListener {
                            resultLiveData.value = true
                        }
                        .addOnFailureListener {
                            resultLiveData.value = false
                        }
                } else {
                    resultLiveData.value = false
                }
            }
        } else {
            fr.collection(Data.mahasiswa)
                .document(profil.userId)
                .update(hm)
                .addOnSuccessListener {
                    resultLiveData.value = true
                }
                .addOnFailureListener {
                    resultLiveData.value = false
                }
        }

        return resultLiveData
    }
}