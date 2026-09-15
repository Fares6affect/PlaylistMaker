package com.example.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson

class SearchHistory(val sharedPrefHistory: SharedPreferences) {
    private val HISTORY_SEARCH = "history_search"
    fun write(tracks: Array<Track>) {
        sharedPrefHistory.edit()
            .putString(HISTORY_SEARCH, Gson().toJson(tracks))
            .apply()
    }
    fun read(): Array<Track> {
        val json = sharedPrefHistory.getString(HISTORY_SEARCH, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }
    fun clear(){
        sharedPrefHistory.edit().remove(HISTORY_SEARCH).apply()
    }
    fun add(track: Track){
        var history = read().toMutableList()
        history.removeAll { it.trackId == track.trackId }
        if(history.size>=10) history.removeAt(9)
        history.add(0,track)
        write(history.toTypedArray())
    }
}