package com.example.sipami.models

class mSurat {
    data class __mKategori (
        val id: String,
        val nama: String
    )

    data class __mSurat (
        val id: String,
        val kategori_id: String,
        val mahasiswa_id: String,
        val tanggal: String,
        val alasan: String,
        val status: Boolean,
        val surat: String
    )
}
