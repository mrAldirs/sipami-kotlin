package com.example.sipami.utils.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityOptionsCompat
import com.example.sipami.R
import com.example.sipami.utils.custom.URLCustom
import com.example.sipami.views.admin.surat._actvHistory
import com.example.sipami.views.colleger.activity._actvWebView
import com.example.sipami.views.colleger.layout._actvMain
import com.example.sipami.views.colleger.layout._actvMaps
import com.example.sipami.views.colleger.profil._actvProfilEdt
import com.example.sipami.views.colleger.surat._actvShow
import com.example.sipami.views.colleger.surat._actvSurat
import com.example.sipami.views.colleger.surat._actvUpdate
import com.example.sipami.views.colleger.user._actvLogin
import com.example.sipami.views.colleger.user._actvRegis

interface IntentHelper {
    fun Context.actionLogin(): Intent {
        return Intent(this, _actvMain::class.java)
    }

    fun Context.actionLogout(): Intent {
        return Intent(this, _actvLogin::class.java)
    }

    fun Context.regis(): Intent {
        return Intent(this, _actvRegis::class.java)
    }

    fun Context.editProf(): Intent {
        return Intent(this, _actvProfilEdt::class.java)
    }

    fun Context.suratShow(data: String): Intent {
        val intent = Intent(this, _actvShow::class.java)
        intent.putExtra("id", data)
        return intent
    }

    fun Context.suratHistoryAdmin(): Intent {
        val intent = Intent(this, _actvHistory::class.java)
        return intent
    }

    fun Context.suratMainAdmin(): Intent {
        val intent = Intent(this, com.example.sipami.views.admin.surat._actvSurat::class.java)
        return intent
    }

    fun Context.documentAdmin(): Intent {
        val intent = Intent(this, com.example.sipami.views.admin.document._actvDocument::class.java)
        return intent
    }

    fun Context.mahasiswaAdmin(): Intent {
        val intent = Intent(this, com.example.sipami.views.admin.mahasiswa._actvMahasiswa::class.java)
        return intent
    }

    fun Context.suratFileAdmin(): Intent {
        val intent = Intent(this, com.example.sipami.views.admin.file._actvFile::class.java)
        return intent
    }

    fun Context.suratShowAdmin(data: String): Intent {
        val intent = Intent(this, com.example.sipami.views.admin.surat._actvShow::class.java)
        intent.putExtra("id", data)
        return intent
    }

    fun Context.suratEdit(data: String): Intent {
        val intent = Intent(this, _actvUpdate::class.java)
        intent.putExtra("id", data)
        return intent
    }

    fun Context.form(): Intent {
        return Intent(this, _actvSurat::class.java)
    }

    fun Context.maps(): Intent {
        return Intent(this, _actvMaps::class.java)
    }

    fun Context.jadwal(): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(URLCustom.JDWL_URL)
        return intent
    }

    fun Context.notelp(): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(URLCustom.NOTELP_URL)
        return intent
    }

    fun Context.himaAction(): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(URLCustom.HIMA_URL)
        return intent
    }

    fun Context.webviewAction(): Intent {
        return Intent(this, _actvWebView::class.java)
    }

    fun Context.intentActivity(intent: Intent) {
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this,
            R.anim.slide_in_right,
            R.anim.stay
        )
        startActivity(intent, options.toBundle())
    }

}