package com.example.sipami.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.sipami.R
import com.example.sipami.models.mKategori
import com.example.sipami.views.admin.layout._actvMain
import com.google.firebase.firestore.FirebaseFirestore

class AdpKategori(private var dataList: List<mKategori>, val remote: _actvMain) :
    RecyclerView.Adapter<AdpKategori.HolderDataRiwayat>(){
    class HolderDataRiwayat (v : View) : RecyclerView.ViewHolder(v) {
        val kategori = v.findViewById<TextView>(R.id.tv_kategori)
        val del = v.findViewById<ImageButton>(R.id.btn_del)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataRiwayat {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.it_kategori, parent, false)
        return HolderDataRiwayat(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: HolderDataRiwayat, position: Int) {
        val data = dataList.get(position)
        holder.kategori.setText(data.nama)

        holder.del.setOnClickListener {
            AlertDialog.Builder(remote)
                .setTitle("Hapus Kategori")
                .setMessage("Apakah anda yakin ingin menghapus kategori ini?")
                .setPositiveButton("Ya") { dialog, which ->
                    FirebaseFirestore.getInstance().collection("kategori").document(data.id).delete()
                        .addOnSuccessListener {
                            Toast.makeText(remote, "Berhasil menghapus kategori", Toast.LENGTH_SHORT).show()
                            remote.recreate()
                        }
                        .addOnFailureListener {
                        }
                }
                .setNegativeButton("Tidak") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    fun setData(newDataList: List<mKategori>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}