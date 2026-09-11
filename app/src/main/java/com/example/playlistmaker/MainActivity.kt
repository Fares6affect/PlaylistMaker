package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val search = findViewById<Button>(R.id.search)
        search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        val media = findViewById<Button>(R.id.media)
        media.setOnClickListener {
            startActivity(Intent(this, MediaActivity::class.java))
        }

        val setting = findViewById<Button>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }



    }
}