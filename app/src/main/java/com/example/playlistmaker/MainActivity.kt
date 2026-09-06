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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val search = findViewById<Button>(R.id.search)
        //val searchClickListener : View.OnClickListener = object : View.OnClickListener{
            //override fun onClick(p0: View?) {
                //Toast.makeText(this@MainActivity,"Нажатие кнопки поиска", Toast.LENGTH_SHORT).show()
            //}
        //}
        //search.setOnClickListener(searchClickListener)

        //val media = findViewById<Button>(R.id.media)
        //media.setOnClickListener { Toast.makeText(this@MainActivity, "Нажатие на кнопку медиатеки", Toast.LENGTH_LONG).show() }

        //val setting = findViewById<Button>(R.id.setting)
        //setting.setOnClickListener { Toast.makeText(this@MainActivity, "Нажатие на кнопку настроек", Toast.LENGTH_LONG).show() }

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