package com.example.sipami.views.admin.layout

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sipami.R
import com.example.sipami.adapter.AdpKategori
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.AdmCDashboardBinding
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.Toast
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.FirebaseFirestore

class _actvMain : AppCompatActivity(), IntentHelper {

    private lateinit var _b: AdmCDashboardBinding
    private lateinit var vmSurat: Surat
    private lateinit var adapter: AdpKategori
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = AdmCDashboardBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("Dashboard")
        vmSurat = ViewModelProvider(this).get(Surat::class.java)

        toggle = ActionBarDrawerToggle(this, _b.drawerLayout, R.string.open, R.string.close)
        _b.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        Toast.init(this)

        adapter = AdpKategori(listOf(), this)
        _b.rvKategori.layoutManager = LinearLayoutManager(this)
        _b.rvKategori.adapter = adapter

        vmSurat.loadKategori().observe(this, Observer { data ->
            adapter.setData(data)
        })

        _b.btnTambah.setOnClickListener {
            val frag = _fragKategori()

            frag.show(supportFragmentManager, "Tambah Kategori")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _b.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    _b.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_user_admin -> {
                    intentActivity(mahasiswaAdmin())
                }
                R.id.nav_build -> {
                    intentActivity(documentAdmin())
                }
                R.id.nav_surat_masuk -> {
                    intentActivity(suratMainAdmin())
                }
                R.id.nav_history -> {
                    intentActivity(suratHistoryAdmin())
                }
                R.id.nav_file_surat -> {
                    intentActivity(suratFileAdmin())
                }
                R.id.nav_logout -> {
                    intentActivity(actionLogout())
                }
            }
            true
        }

        getChart()
    }

    private fun getChart() {
        vmSurat.getChart().observe(this, Observer { data ->
            val barEntries = ArrayList<BarEntry>()
            for (i in data.indices) {
                barEntries.add(BarEntry(i.toFloat(), data[i].count.toFloat(), data[i].name))
            }
            setupBarChart(barEntries)
        })
    }

    private fun setupBarChart(barEntries: List<BarEntry>) {
        val dataSet = BarDataSet(barEntries, "Data Set")
        dataSet.setColors(*ColorTemplate.PASTEL_COLORS)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(dataSet)

        val data = BarData(dataSets)

        _b.barChart.data = data
        _b.barChart.description.isEnabled = false
        _b.barChart.xAxis.setDrawGridLines(false)
        _b.barChart.xAxis.setDrawAxisLine(true)
        _b.barChart.xAxis.setDrawLabels(true)
        _b.barChart.xAxis.labelCount = barEntries.size

        val labels = ArrayList<String>()
        for (entry in barEntries) {
            labels.add(entry.data as String)
        }
        val xAxis = _b.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        _b.barChart.axisRight.isEnabled = false
        _b.barChart.legend.isEnabled = false
        _b.barChart.animateY(1000, Easing.EaseInOutCubic)

        _b.barChart.invalidate()
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

    fun deleteKategori(id: String) {

    }
}