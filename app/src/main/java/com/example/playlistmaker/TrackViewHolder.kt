package com.example.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val artworkView: ImageView
    private val title: TextView
    private val author: TextView
    private val time: TextView

    init{
        artworkView = itemView.findViewById(R.id.artwork)
        title = itemView.findViewById(R.id.title)
        author = itemView.findViewById(R.id.author)
        time = itemView.findViewById(R.id.time)
    }

    fun bind(track:Track){
        Glide.with(itemView).load(track.artworkUrl100).placeholder(R.drawable.ic_artwork_45).into(artworkView)
        title.text=track.trackName
        author.text=track.artistName
        time.text=track.trackTime
    }

}