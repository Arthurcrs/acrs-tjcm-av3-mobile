package com.example.myweather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_settings.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        // Settings - SharedPreferences
        rb_celsius.setOnClickListener {
            saveData()
        }
        rb_fahrenheit.setOnClickListener {
            saveData()
        }
        rb_english.setOnClickListener {
            saveData()
        }
        rb_portuguese.setOnClickListener {
            saveData()
        }

        // Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

    }

    // SharedPreferences
    private fun saveData() {
        val celsiusTempSelected = rb_celsius.isChecked()
        val englishLanguageSelected = rb_english.isChecked()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.apply {
            putBoolean("CELSIUS SELECTED", celsiusTempSelected)
            putBoolean("ENGLISH SELECTED", englishLanguageSelected)
        }.apply()
    }

    // SharedPreferences
    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val celsiusTempSelected = sharedPreferences.getBoolean("CELSIUS SELECTED", true)
        val englishLanguageSelected = sharedPreferences.getBoolean("ENGLISH SELECTED", true)

        if (celsiusTempSelected == true) {
            rb_celsius.setChecked(true)
        } else {
            rb_fahrenheit.setChecked(true)
        }

        if (englishLanguageSelected == true) {
            rb_english.setChecked(true)
        } else {
            rb_portuguese.setChecked(true)
        }
    }

}