package com.enes.busproje.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enes.busproje.R
import com.enes.busproje.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}