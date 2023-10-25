package com.example.sipami.models

class mSurat {
    data class __mKategori (
        val id: String,
        val nama: String
    )

    data class __mSurat (
        val id: String,
        val kategori_id: String,
        val user_id: String,
        val tanggal: String,
        val semester: String,
        val alasan: String,
        val status: String,
        val file: String
    )

    data class __mHistory (
        val id: String,
        val tanggal: String,
        val status: String
    )
}
