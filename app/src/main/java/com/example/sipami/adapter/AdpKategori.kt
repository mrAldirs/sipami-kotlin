package com.example.sipami.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sipami.R
import com.example.sipami.models.mKategori
import com.example.sipami.models.mSurat
import com.example.sipami.views.admin.layout._actvMain
import com.example.sipami.views.admin.surat._actvHistory
import com.example.sipami.views.admin.surat._actvSurat

class AdpKategori(private var dataList: List<mKategori>, private val remote: _actvMain) :
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
            remote.delete(data.id)
        }
    }

    fun setData(newDataList: List<mKategori>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}