package com.example.sipami.utils.helper

import android.content.Context
import android.widget.Toast

object Toast {

    private var applicationContext: Context? = null

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    var sukses1 = "Masuk ke Akun Email untuk Verifikasi Akun!"
    var sukses2 = "Berhasil mengirim data"
    var eror1 = "Lengkapi data terlebih dahulu!"
    var eror2 = "Katasandi salah!"

    fun message(message: String) {
        toastMessage(message, Toast.LENGTH_SHORT)
    }

    private fun toastMessage(message: String, duration: Int) {
        applicationContext?.let {
            Toast.makeText(it, message, duration).show()
        }
    }
}
