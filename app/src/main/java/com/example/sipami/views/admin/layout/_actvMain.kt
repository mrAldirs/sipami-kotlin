package com.example.sipami.views.admin.layout

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.sipami.R
import com.example.sipami.databinding.AdmCDashboardBinding

class _actvMain : AppCompatActivity() {

    private lateinit var _b: AdmCDashboardBinding
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = AdmCDashboardBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("Dashboard")

        toggle = ActionBarDrawerToggle(this, _b.drawerLayout, R.string.open, R.string.close)
        _b.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _b.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    _b.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_surat_masuk -> {

                }
            }
            true
        }
    }

    override fun onBackPressed() {
        if (_b.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            _b.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}