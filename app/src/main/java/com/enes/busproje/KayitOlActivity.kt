package com.enes.busproje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.enes.busproje.databinding.ActivityKayitOlBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_kayit_ol.*

class KayitOlActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKayitOlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnGiriseDon.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun bttnKaydet(view: View) {
        val mail = kayitKullaniciMail.text.toString()
        val sifre = kayitKullaniciParola.text.toString()
        if (mail.isEmpty() && sifre.isEmpty()){
            Toast.makeText(applicationContext,"Kullanıcı adı ve şifre boş geçilemez",Toast.LENGTH_LONG).show()
            return
        }
        auth.createUserWithEmailAndPassword(mail, sifre).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val reason = task.exception
                Toast.makeText(applicationContext,"Kullanıcı kayıt edilirken hata ile karşılaşıldı. \n $reason",
                    Toast.LENGTH_LONG).show()

            }
        }
    }
}