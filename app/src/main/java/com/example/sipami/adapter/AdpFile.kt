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
import com.example.sipami.models.mSurat

class AdpFile(private var dataList: List<mSurat.__mFile>) :
    RecyclerView.Adapter<AdpFile.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val nos = v.findViewById<TextView>(R.id.item_nos)
        val name = v.findViewById<TextView>(R.id.item_name)
        val pdf = v.findViewById<ImageView>(R.id.btn_pdf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.it_file, parent, false)
        return HolderData(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = dataList.get(position)
        holder.name.text = data.kategori_name
        holder.nos.text = "No. Surat: ${data.nos}"

        holder.pdf.setOnClickListener {
            val pdf = data.file.toUri()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(pdf, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setData(newDataList: List<mSurat.__mFile>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}