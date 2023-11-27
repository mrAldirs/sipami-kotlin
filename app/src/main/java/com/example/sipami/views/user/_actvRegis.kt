package com.example.sipami.views.user

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sipami.R
import com.example.sipami.api.viewmodel.User
import com.example.sipami.databinding.CRegisBinding
import com.example.sipami.utils.helper.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class _actvRegis : AppCompatActivity() {
    private lateinit var _b: CRegisBinding
    private lateinit var vmUser: User
    lateinit var uri: Uri
    val RC_P = 100
    var role = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = CRegisBinding.inflate(layoutInflater)
        setContentView(_b.root)
        supportActionBar?.setTitle("Registrasi Akun")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Toast.init(applicationContext)
        vmUser = ViewModelProvider(this).get(User::class.java)
        uri = Uri.EMPTY

        registrasi()
        setImage()

        _b.rgRole.setOnCheckedChangeListener { group, checked ->
            when(checked) {
                R.id.rb_mahasiswa -> role = "mahasiswa"
                R.id.rb_admin -> role = "admin"
            }
        }
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

    private fun setImage() {
        _b.btnChoose.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setType("image/*")
            startActivityForResult(intent, RC_P)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) && (requestCode == RC_P)) {
            if (data != null){
                uri = data.data!!
                Picasso.get().load(uri.toString()).into(_b.insImg)
            }
        }
    }

    private fun validasi(uri: Uri) {
        vmUser.createUserWithEmailAndPassword(_b.insEmail.text.toString(), _b.insPassword.text.toString())

        val randomId = UUID.randomUUID().toString()
        val hm = HashMap<String, Any>()
        hm.set("id", randomId)
        hm.set("email", _b.insEmail.text.toString())
        hm.set("password", _b.insPassword.text.toString())
        hm.set("role", role)

        FirebaseFirestore.getInstance().collection("user")
            .document(_b.insEmail.text.toString())
            .set(hm)
            .addOnSuccessListener {
                val fileName = "IMG" + SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date()) + "new"
                val fileRef = FirebaseStorage.getInstance().reference.child(fileName + ".jpg")
                val uploadTask = fileRef.putFile(uri)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    fileRef.downloadUrl
                }.addOnCompleteListener {
                    val randomId2 = UUID.randomUUID().toString()
                    val hm = HashMap<String, Any>()
                    hm.set("id", randomId2)
                    hm.set("user_id", randomId)
                    hm.set("nama", _b.insNama.text.toString())
                    hm.set("nim", _b.insNim.text.toString())
                    hm.set("foto", it.result.toString())

                    FirebaseFirestore.getInstance().collection("mahasiswa")
                        .document(randomId)
                        .set(hm)
                        .addOnSuccessListener {
                            Toast.message(Toast.sukses1)
                            onBackPressed()
                        }
                        .addOnFailureListener {

                        }
                    }
                .addOnFailureListener {

                }
            }
    }

    private fun nothing(): Boolean {
        val nama = _b.insNama.text.toString()
        val nim = _b.insNim.text.toString()
        val email = _b.insEmail.text.toString()
        val pw = _b.insPassword.text.toString()
        val conf = _b.insKonfirm.text.toString()

        return (nama.isEmpty() || nim.isEmpty() || email.isEmpty() || pw.isEmpty() || conf.isEmpty())
    }

    private fun konfirm(): Boolean {
        val pw = _b.insPassword.text.toString()
        val conf = _b.insKonfirm.text.toString()
        return (pw == conf)
    }

    private fun registrasi() {
        _b.btnDaftarkan.setOnClickListener {
            if (nothing()) {
                Toast.message(Toast.eror1)
            } else {
                if (konfirm()) {
                    validasi(uri)
                } else {
                    Toast.message(Toast.eror2)
                }
            }
        }
    }
}