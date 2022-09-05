package com.enes.busproje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.enes.busproje.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val guncelKullanici =auth.currentUser
        if(guncelKullanici != null){
            val intent = Intent(this,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnKayitOl.setOnClickListener{
            intent = Intent(applicationContext,KayitOlActivity::class.java)
            startActivity(intent)
        }
    }
    fun btnGiris(view: View){
        auth.signInWithEmailAndPassword(girisKullaniciMail.text.toString(),girisParola.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val guncelKullanici = auth.currentUser?.email.toString()
                Toast.makeText(this,"Hoşgeldin: ${guncelKullanici}",Toast.LENGTH_LONG).show()

                val intent = Intent(this,GirisActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}