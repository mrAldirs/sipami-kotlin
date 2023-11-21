package com.example.sipami.utils.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityOptionsCompat
import com.example.sipami.R
import com.example.sipami.utils.custom.URLCustom
import com.example.sipami.views.layout._actvMain
import com.example.sipami.views.profil._actvProfilEdt
import com.example.sipami.views.surat._actvShow
import com.example.sipami.views.surat._actvSurat
import com.example.sipami.views.user._actvLogin
import com.example.sipami.views.user._actvRegis

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

    fun Context.form(): Intent {
        return Intent(this, _actvSurat::class.java)
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

    fun Context.intentActivity(intent: Intent) {
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this,
            R.anim.slide_in_right,
            R.anim.stay
        )
        startActivity(intent, options.toBundle())
    }
}