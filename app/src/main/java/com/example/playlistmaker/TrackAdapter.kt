package com.example.playlistmaker

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class TrackAdapter(
    private var tracks: List<Track>,
    private val clickTrack: (Track) -> Unit
): RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view,parent,false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            clickTrack(tracks[position])
        }
    }

    override fun getItemCount(): Int = tracks.size

    fun setTracks(newTracks: List<Track>){
        tracks = newTracks
        notifyDataSetChanged()
    }

}