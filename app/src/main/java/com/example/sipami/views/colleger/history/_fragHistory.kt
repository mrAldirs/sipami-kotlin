package com.example.sipami.views.colleger.history

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sipami.adapter.AdpHistory
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.FHistoryBinding
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.SharedPreferences
import com.example.sipami.utils.helper.Toast

open class _fragHistory : Fragment(), IntentHelper {
    private lateinit var _b : FHistoryBinding
    lateinit var _v: View
    private lateinit var vmSurat : Surat
    private lateinit var preferences: SharedPreferences
    private lateinit var adapter: AdpHistory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _b = FHistoryBinding.inflate(layoutInflater)
        _v = _b.root
        Toast.init(_v.context)
        vmSurat = ViewModelProvider(this).get(Surat::class.java)
        preferences = SharedPreferences(_v.context)

        return _v
    }

    fun show(id: String) {
        activity?.suratShow(id)?.let { requireActivity().intentActivity(it) }
    }

    private fun listItem() {
        adapter = AdpHistory(ArrayList(), this)
        _b.recyclerView.layoutManager = LinearLayoutManager(_v.context)
        _b.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        listItem()
        vmSurat.loadData(preferences.getString("id", "")).observe(this, Observer { history ->
            adapter.setData(history)
        })
    }

    fun delete(id: String) {
        AlertDialog.Builder(_v.context)
            .setTitle("Hapus!")
            .setIcon(android.R.drawable.stat_sys_warning)
            .setMessage("Apakah Anda ingin menghapus history ini?")
            .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                vmSurat.delete(id).observe(this, Observer { result ->
                    loadData()
                    Toast.message("Berhasil menghapus data!")
                })
            })
            .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
            })
            .show()
    }
}