package com.example.sipami.api.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sipami.api.repository.__profil___
import com.example.sipami.models.mProfil

class Profil: ViewModel() {
    private val __profil = __profil___()

    fun profil(id: String): LiveData<mProfil> {
        return __profil.getProfil(id)
    }

    fun edit(profil: mProfil, uri: Uri?) : LiveData<Boolean> {
        return __profil.edit(profil, uri)
    }
}