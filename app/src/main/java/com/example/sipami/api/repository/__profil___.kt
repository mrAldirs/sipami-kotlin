package com.example.sipami.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sipami.api.global.Config
import com.example.sipami.api.global.Data
import com.example.sipami.models.mProfil

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
}