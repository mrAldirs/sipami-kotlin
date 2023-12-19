package com.example.sipami.views.colleger.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.sipami.databinding.CKegiatanBinding
import androidx.appcompat.app.AppCompatActivity
import com.example.sipami.utils.custom.URLCustom

class _actvWebView : AppCompatActivity() {
    private lateinit var _b: CKegiatanBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CKegiatanBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.hide()

        _b.webView.loadUrl(URLCustom.SIPAMI_URL)
        _b.webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }
        _b.webView.webViewClient = android.webkit.WebViewClient()
        _b.webView.webChromeClient = android.webkit.WebChromeClient()

    }

    override fun onBackPressed() {
        if (_b.webView.canGoBack()) {
            _b.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}