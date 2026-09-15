package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate



class App: Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()
        switchTheme(getSharedPreferences(PLAYLIST_MAKER_PREFERENCES,MODE_PRIVATE).getBoolean(NIGHT_MODE,false))
    }

    fun switchTheme(darkThemeEnabled: Boolean){
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if(darkThemeEnabled){
                AppCompatDelegate.MODE_NIGHT_YES
            }
            else{
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}