package com.example.playlistmaker

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class TrackAdapter(
    private var tracks: List<Track>,
    private val clickTrack: (Track) -> Unit
): RecyclerView.Adapter<TrackViewHolder>() {

    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view,parent,false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            val track = tracks[position]
            clickTrack(track)
            val context = holder.itemView.context
            val intent = Intent(context, AudioPlayerActivity::class.java).apply {
                putExtra(TRACK_NAME,track.trackName)
                putExtra(ARTIST_NAME,track.artistName)
                putExtra(TRACK_TIME_MILLIS,dateFormat.format(track.trackTimeMillis?:0L))
                putExtra(ARTWORK_URL,track.artworkUrl100?.replaceAfterLast('/',"512x512bb.jpg"))
                putExtra(COLLECTION_NAME,track.collectionName)
                putExtra(RELEASE_DATE,track.releaseDate)
                putExtra(PRIMARY_GENRE_NAME,track.primaryGenreName)
                putExtra(COUNTRY,track.country)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tracks.size

    fun setTracks(newTracks: List<Track>){
        tracks = newTracks
        notifyDataSetChanged()
    }

}