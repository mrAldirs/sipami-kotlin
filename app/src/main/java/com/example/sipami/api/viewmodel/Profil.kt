package com.example.sipami.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sipami.api.repository.__profil___
import com.example.sipami.models.mProfil

class Profil: ViewModel() {
    private val profil = __profil___()

    fun profil(id: String): LiveData<mProfil> {
        return profil.getProfil(id)
    }
}