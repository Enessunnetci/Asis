package com.enes.busproje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = FirebaseAuth.getInstance()
        btnChangePassword2.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword(){

        if (textCurrentPassword.text.isNotEmpty() &&
                textNewPassword.text.isNotEmpty() &&
                textConfirmPassword.text.isNotEmpty()){

            if (textNewPassword.text.toString().equals(textConfirmPassword.text.toString())){
                val user : FirebaseUser? = auth.currentUser
                if(user!=null && user.email != null ){
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, textCurrentPassword.text.toString())

// Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(this, "Re-Authentication success", Toast.LENGTH_SHORT).show()
                                user!!.updatePassword(textNewPassword.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                                            auth.signOut()
                                            startActivity(Intent(this,MainActivity::class.java))
                                            finish()
                                        }
                                    }

                            }
                            else{
                                Toast.makeText(this, "Re-Authentication failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }

            }
            else{
                Toast.makeText(this, "Password mismatching", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}