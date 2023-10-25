package com.example.sipami.api.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sipami.api.global.Config
import com.example.sipami.api.global.Data
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