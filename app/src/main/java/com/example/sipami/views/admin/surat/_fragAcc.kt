package com.example.sipami.views.admin.surat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sipami.databinding.AdmFSuratAccBinding
import com.example.sipami.utils.helper.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class _fragAcc : BottomSheetDialogFragment() {
    private lateinit var _b: AdmFSuratAccBinding
    lateinit var _v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _b = AdmFSuratAccBinding.inflate(layoutInflater)
        _v = _b.root

        val kode = arguments?.getString("id")
        Toast.init(this.requireContext())
        Toast.message("Kode: $kode")

        return _v
    }
}