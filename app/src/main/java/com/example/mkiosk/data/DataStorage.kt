package com.example.mkiosk.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mkiosk.R
import com.example.mkiosk.util.Util.accountMap
import com.example.mkiosk.util.Util.recommendationMap
import com.example.mkiosk.util.Util.songList
import com.example.mkiosk.util.Util.toast
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

object DataStorage {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "MusicKiosk")
    val granted = booleanPreferencesKey("granted")
    val songs = stringPreferencesKey("song_list")

    suspend fun grant(context: Context) {
        try {
            context.dataStore.edit { data ->
                data[granted] = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            context.toast(R.string.error)
        }

    }

    suspend fun isGranted(context: Context) : Boolean {
        try {
            return context.dataStore.data.map { it[granted] }.first() ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun storeSongs(context: Context) {
        try {
            Log.d("TEST", "STORE: ${Json.encodeToString(accountMap.toMap())}")
            context.dataStore.edit { data ->
                data[songs] = Json.encodeToString(accountMap.toMap())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            context.toast(R.string.error)
        }
    }

    suspend fun readSongs(context: Context) {
        try {
            val songData = Json.decodeFromString<MutableMap<String, MutableList<Song>>>(context.dataStore.data.map { it[songs] }.first().toString())
            accountMap = songData
            for (song in songList) {
                recommendationMap[song.id] = mutableListOf()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            context.toast(R.string.error)
        }

    }

}