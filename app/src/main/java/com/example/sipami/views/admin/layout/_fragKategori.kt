package com.example.sipami.views.admin.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.AdmFKategoriBinding
import com.example.sipami.models.mKategori
import com.example.sipami.utils.helper.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.UUID

class _fragKategori : BottomSheetDialogFragment() {
    private lateinit var _b : AdmFKategoriBinding
    private lateinit var vmSurat : Surat
    private lateinit var _v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _b = AdmFKategoriBinding.inflate(inflater, container, false)
        _v = _b.root

        vmSurat = ViewModelProvider(this).get(Surat::class.java)
        Toast.init(requireContext())

        _b.btnSubmit.setOnClickListener {
            val dataList = mKategori(
                UUID.randomUUID().toString(),
                _b.insKategori.text.toString()
            )

            vmSurat.insertKategori(dataList).observe(this, Observer {
                if (it) {
                    dismiss()
                    Toast.message("Berhasil menambahkan kategori")
                    requireActivity().recreate()
                }

            })
        }

        return _v
    }
}