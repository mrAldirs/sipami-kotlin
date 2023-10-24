package com.example.sipami.utils.helper

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.example.sipami.R
import com.example.sipami.views.layout._actvMain
import com.example.sipami.views.surat._actvSurat
import com.example.sipami.views.user._actvRegis

interface IntentHelper {
    fun Context.actionLogin(): Intent {
        return Intent(this, _actvMain::class.java)
    }

    fun Context.regis(): Intent {
        return Intent(this, _actvRegis::class.java)
    }

    fun Context.form(): Intent {
        return Intent(this, _actvSurat::class.java)
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