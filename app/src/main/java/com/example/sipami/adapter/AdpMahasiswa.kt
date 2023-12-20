package com.example.sipami.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.sipami.R
import com.example.sipami.models.mMahasiswa
import com.example.sipami.models.mSurat
import com.squareup.picasso.Picasso

class AdpMahasiswa(private var dataList: List<mMahasiswa>) :
    RecyclerView.Adapter<AdpMahasiswa.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.item_name)
        val nim = v.findViewById<TextView>(R.id.item_nim)
        val foto = v.findViewById<ImageView>(R.id.item_foto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.it_mahasiswa, parent, false)
        return HolderData(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = dataList.get(position)
        holder.name.text = data.name
        holder.nim.text = "NIM: ${data.nim}"
        Picasso.get().load(data.foto.toUri()).into(holder.foto)
    }

    fun setData(newDataList: List<mMahasiswa>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}