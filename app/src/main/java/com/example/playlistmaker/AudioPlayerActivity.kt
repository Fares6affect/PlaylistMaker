package com.example.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.imageview.ShapeableImageView

class AudioPlayerActivity: AppCompatActivity() {

    var fPause = false
        private set

    var fLike = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audioplayer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        Glide.with(this).load(intent.getStringExtra("artworkUrl100")?:"").placeholder(R.drawable.album_placeholder).into(findViewById<ShapeableImageView>(R.id.artwork))

        findViewById<TextView>(R.id.trackName).setText(intent.getStringExtra("trackName"))

        findViewById<TextView>(R.id.artistName).setText(intent.getStringExtra("artistName"))

        findViewById<TextView>(R.id.time).setText("00:00")

        findViewById<TextView>(R.id.durationValue).setText(intent.getStringExtra("trackTimeMillis"))

        val alb = intent.getStringExtra("collectionName")
        if(alb.isNullOrEmpty())
            findViewById<Group>(R.id.albumGroup).visibility = View.GONE
        else
            findViewById<TextView>(R.id.albumValue).setText(alb)

        val year = intent.getStringExtra("releaseDate")?.substring(0,4)
        if(year.isNullOrEmpty())
            findViewById<Group>(R.id.yearGroup).visibility = View.GONE
        else
            findViewById<TextView>(R.id.yearValue).setText(year)

        findViewById<TextView>(R.id.genreValue).setText(intent.getStringExtra("primaryGenreName"))

        findViewById<TextView>(R.id.countryValue).setText(intent.getStringExtra("country"))

        val pauseResumeButton = findViewById<ImageButton>(R.id.pauseResumeButton)
        pauseResumeButton.setOnClickListener {
            if(fPause)
                pauseResumeButton.setImageResource(R.drawable.ic_pausebutton_100)
            else
                pauseResumeButton.setImageResource(R.drawable.ic_resumebutton_100)
            fPause=!fPause
        }

        val likeButton = findViewById<ImageButton>(R.id.likeButton)
        likeButton.setOnClickListener {
            if(fLike)
                likeButton.setImageResource(R.drawable.ic_nonlike_button_51)
            else
                likeButton.setImageResource(R.drawable.ic_likebutton_51)
            fLike=!fLike
        }

    }
}