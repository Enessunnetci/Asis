package com.enes.busproje

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.enes.busproje.databinding.ActivityGirisBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class GirisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGirisBinding
    lateinit var bottomNavigationView : BottomNavigationView
    private val blankFragment = HomeFragment()
    private val busFragment = LinesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null

        /*  bottomNavigationView.setOnItemSelectedListener {
              when(it.itemId) {
                  R.id.home-> {
                      supportFragmentManager.beginTransaction().replace(R.id.frameLayout,BlankFragment())
                  }
              }
              return@setOnItemSelectedListener false
          }*/
        makeCurrentFragment(blankFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> makeCurrentFragment(blankFragment)
                R.id.lines->{
                    val linesFragment = LinesFragment()
                    makeCurrentFragment(linesFragment)
                }
                R.id.settings-> {
                    val settingsFragment = SettingsFragment()
                    makeCurrentFragment(settingsFragment)
                }
                else -> return@setOnItemSelectedListener true

            }
            return@setOnItemSelectedListener true
        }


    }
    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}