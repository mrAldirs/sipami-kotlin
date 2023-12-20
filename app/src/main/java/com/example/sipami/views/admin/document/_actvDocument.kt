package com.example.sipami.views.admin.document

import android.R
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.AdmCDocumentBinding
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class _actvDocument : AppCompatActivity() {
    private lateinit var _b : AdmCDocumentBinding
    private lateinit var vmSurat : Surat
    private val STORAGE_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = AdmCDocumentBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.title = "Document"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vmSurat = ViewModelProvider(this).get(Surat::class.java)
        kategori()

        _b.btnSubmit.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                } else {
                    savePDF()
                }
            } else {
                savePDF()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun kategori() {
        vmSurat.getKategori { __mKategoris, exception ->
            if (__mKategoris != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Keperluan Surat --")

                for (kategori in __mKategoris) {
                    dataList.add(kategori.nama)
                }

                val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dataList)
                _b.insKategori.adapter = adapter
            }
        }
    }

    private fun savePDF() {
        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(System.currentTimeMillis())
        val mFilePath = getExternalFilesDir(null)?.absolutePath + "/" + mFileName + ".pdf"

        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()

            val headText = _b.insKategori.selectedItem.toString()
            val noText = _b.insNo.text.toString()
            val bodyText = _b.insBody.text.toString()
            val creatorText = _b.insCreator.text.toString()

            val para = "Keperluan: $headText\nNo : $noText\n\n$bodyText\n\n$creatorText"

            mDoc.addAuthor("SIPAMI")
            mDoc.add(Paragraph(para))
            mDoc.close()
            Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            STORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePDF()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}