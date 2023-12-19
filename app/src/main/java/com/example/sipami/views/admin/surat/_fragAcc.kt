package com.example.sipami.views.admin.surat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.R
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.AdmFSuratAccBinding
import com.example.sipami.utils.helper.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class _fragAcc : BottomSheetDialogFragment() {
    private lateinit var _b: AdmFSuratAccBinding
    private lateinit var vmSurat: Surat
    lateinit var _v: View
    lateinit var uri: Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _b = AdmFSuratAccBinding.inflate(layoutInflater)
        _v = _b.root

        vmSurat = ViewModelProvider(this).get(Surat::class.java)
        val kode = arguments?.getString("id").toString()
        uri = Uri.EMPTY
        Toast.init(_v.context)

        _b.imgPdf.setBackgroundResource(R.drawable.ic_box)
        _b.btnUpload.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setType("application/pdf")
            startActivityForResult(intent, 10)
        }

        val nos = _b.insNos.text.toString()

        _b.btnKonfirmasi.setOnClickListener {
            vmSurat.uploadFile(kode, nos, uri).observe(this, Observer {
                Toast.message("Berhasil mengkonfirmasi pengajuan surat")
                dismiss()
                requireActivity().recreate()
            })
        }

        return _v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) && (requestCode == 10)) {
            if (data != null){
                uri = data.data!!
                _b.imgPdf.setBackgroundResource(R.drawable.ic_pdf)
                _b.tvFileName.text = uri.lastPathSegment
            }
        }
    }


}