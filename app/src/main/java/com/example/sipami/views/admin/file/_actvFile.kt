package com.example.sipami.views.admin.file

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sipami.adapter.AdpFile
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.CFileBinding

class _actvFile : AppCompatActivity() {
    private lateinit var _b: CFileBinding
    private lateinit var vmSurat : Surat
    private lateinit var adapter : AdpFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CFileBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("File Surat")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vmSurat = ViewModelProvider(this).get(Surat::class.java)

        adapter = AdpFile(listOf())
        _b.rvFiles.layoutManager = LinearLayoutManager(this)
        _b.rvFiles.adapter = adapter

        vmSurat.loadFile().observe(this@_actvFile, Observer { data ->
            adapter.setData(data)
        })

        _b.inpFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                if (selectedItem == "-- Filter Surat --") {
                    vmSurat.loadFile().observe(this@_actvFile, Observer { data ->
                        adapter.setData(data)
                    })
                } else {
                    vmSurat.loadFile(selectedItem).observe(this@_actvFile, Observer { data ->
                        adapter.setData(data)
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        kategori()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun kategori() {
        vmSurat.getKategori { __mKategoris, exception ->
            if (__mKategoris != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Filter Surat --")

                for (kategori in __mKategoris) {
                    dataList.add(kategori.nama)
                }

                val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dataList)
                _b.inpFilter.adapter = adapter
            }
        }
    }
}