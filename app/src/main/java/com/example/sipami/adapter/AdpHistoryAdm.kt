package com.example.sipami.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sipami.R
import com.example.sipami.models.mSurat
import com.example.sipami.views.admin.surat._actvSurat

class AdpHistoryAdm(private var dataList: List<mSurat.__mHistory>, val remote: _actvSurat) :
    RecyclerView.Adapter<AdpHistoryAdm.HolderDataRiwayat>(){
    class HolderDataRiwayat (v : View) : RecyclerView.ViewHolder(v) {
        val title = v.findViewById<TextView>(R.id.tv_title)
        val sub = v.findViewById<TextView>(R.id.tv_subtitle)
        val tgl = v.findViewById<TextView>(R.id.tv_tanggal)
        val sts = v.findViewById<TextView>(R.id.tv_status)
        val cd = v.findViewById<CardView>(R.id.cd_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataRiwayat {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.it_history, parent, false)
        return HolderDataRiwayat(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: HolderDataRiwayat, position: Int) {
        val data = dataList.get(position)
        holder.tgl.setText(data.tanggal)
        holder.sts.setText(data.status)
        if (data.status.equals("On Process")) {
            holder.title.text = "Laporan Surat"
            holder.sub.text = "Menunggu persetujuan dari sistem"
            holder.sts.setBackgroundColor(Color.parseColor("#E4A03B"))
        } else {
            holder.title.text = "Jadwal Terbaru"
            holder.sub.text = "Jadwal terbaru sudah dirilis"
            holder.sts.setBackgroundColor(Color.parseColor("#03A9F4"))
        }

        holder.cd.setOnLongClickListener {
            remote.delete(data.id)
            true
        }

        holder.cd.setOnClickListener {
            remote.show(data.id)
        }
    }

    fun setData(newDataList: List<mSurat.__mHistory>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}