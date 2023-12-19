package com.example.sipami.models

class mSurat {
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

    data class __mSuratAll (
        val id: String,
        val kategori_id: String,
        val kategori_name: String,
        val user_id: String,
        val tanggal: String,
        val semester: String,
        val alasan: String,
        val status: String,
        val file: String
    )

    data class __mChartModel (
        val name: String,
        val count: Long
    )

    data class __mFile (
        val id: String,
        val kategori_name: String,
        val nos: String,
        val file: String
    )

    data class __mHistory (
        val id: String,
        val tanggal: String,
        val status: String
    )
}
