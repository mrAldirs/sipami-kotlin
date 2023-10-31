package com.example.sipami.views.surat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sipami.R
import com.example.sipami.api.viewmodel.Profil
import com.example.sipami.api.viewmodel.Surat
import com.example.sipami.databinding.CSuratBinding
import com.example.sipami.databinding.FormSuratBinding
import com.example.sipami.models.mSurat
import com.example.sipami.utils.helper.DatePickerHelper
import com.example.sipami.utils.helper.IntentHelper
import com.example.sipami.utils.helper.SharedPreferences
import com.example.sipami.utils.helper.Toast
import java.util.*

class _actvSurat : AppCompatActivity(), IntentHelper {
    private lateinit var _b: CSuratBinding
    private lateinit var _b_content: FormSuratBinding
    private lateinit var vmProfil: Profil
    private lateinit var vmSurat: Surat
    private lateinit var preferences: SharedPreferences
    private lateinit var datePickerHelper: DatePickerHelper
    private val kategoriId = mutableListOf<String>()
    private var getKategori = ""
    val uuid = UUID.randomUUID().toString()

    val semester = arrayOf("Pilih Semester","1","2","3","4","5","6","7","8")
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CSuratBinding.inflate(layoutInflater)
        _b_content = _b.content
        setContentView(_b.root)
        supportActionBar?.setTitle("Formulir Pengajuan Surat")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Toast.init(this)
        vmProfil = ViewModelProvider(this).get(Profil::class.java)
        vmSurat = ViewModelProvider(this).get(Surat::class.java)
        preferences = SharedPreferences(this@_actvSurat)
        datePickerHelper = DatePickerHelper(this)

        date()
        semester()
        inputKategori()
        submit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }

    private fun semester() {
        adapter = ArrayAdapter(this@_actvSurat, android.R.layout.simple_list_item_1, semester)
        _b_content.insSemester.adapter = adapter
    }

    private fun date() {
        _b_content.insTanggal.setOnClickListener {
            datePickerHelper.showDatePickerDialog(_b_content.insTanggal)
        }
    }

    fun notifikasiMessage() {
        val queue = Volley.newRequestQueue(this)

        val url = "http://192.168.137.1/api_sipami/notification_json.php"

        val postData = HashMap<String, String>()

        val request = object : StringRequest(Method.POST, url, Response.Listener { response ->
        }, Response.ErrorListener { error ->
        }) {
            override fun getParams(): Map<String, String> {
                return postData
            }
        }

        queue.add(request)
    }

    override fun onStart() {
        super.onStart()
        loadData()
        kategori()
    }

    private fun loadData() {
        vmProfil.profil(preferences.getString("id", "")).observe(this@_actvSurat, Observer {
            _b_content.insNama.setText(it.nama)
            _b_content.insNim.setText(it.nim)
            if (it.alamat.equals("null")) { _b_content.insAlamat.setText("<None>") } else { _b_content.insAlamat.setText(it.alamat) }
        })
    }

    private fun kategori() {
        vmSurat.getKategori { __mKategoris, exception ->
            if (__mKategoris != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Pilih Layanan --")

                for (kategori in __mKategoris) {
                    dataList.add(kategori.nama)
                    kategoriId.add(kategori.id)
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
                _b_content.insKategori.adapter = adapter
            }
        }
    }

    private fun inputKategori() {
        _b_content.insKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    getKategori = kategoriId[position - 1]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun validasi() {
        val data = mSurat.__mSurat(
            uuid,
            getKategori,
            preferences.getString("id", ""),
            _b_content.insTanggal.text.toString(),
            _b_content.insSemester.selectedItem.toString(),
            _b_content.insAlasan.text.toString(),"On Process", ""
        )

        vmSurat.insertSurat(data).observe(this, Observer { success ->
            if (success == true) {
                notifikasiMessage()
                onBackPressed()
                Toast.message(Toast.sukses2)
            } else {
                Toast.message(Toast.eror1)
            }
        })
    }

    private fun nothing(): Boolean {
        val tanggal = _b_content.insTanggal.text.toString()
        val alasan = _b_content.insAlasan.text.toString()

        return (tanggal.isEmpty() || alasan.isEmpty())
    }

    private fun submit() {
        _b.btnSubmit.setOnClickListener {
            if (nothing()) {
                Toast.message(Toast.eror1)
            } else {
                validasi()
            }
        }
    }
}