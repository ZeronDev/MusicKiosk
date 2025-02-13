package com.example.mkiosk.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mkiosk.util.Util.accountMap
import com.example.mkiosk.util.Util.recommendationMap
import com.example.mkiosk.util.Util.songList
import kotlinx.coroutines.flow.filterNotNull
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
        }

    }

    suspend fun isGranted(context: Context) : Boolean {
        try {
            return context.dataStore.data.map { it[granted] }.filterNotNull().first()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun storeSongs(context: Context) {
        try {
            context.dataStore.edit { data ->
                data[songs] = Json.encodeToString(songList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun readSongs(context: Context) {
        try {
            val songData = Json.decodeFromString<List<Song>>(context.dataStore.data.map { it[songs] }.filterNotNull().first().toString())
            accountMap["NULL"] = songData.toMutableList()
            for (song in songData) {
                recommendationMap[song.id] = mutableListOf()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}