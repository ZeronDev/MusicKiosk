package com.example.mkiosk.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.example.mkiosk.data.Song
import java.util.UUID

typealias Changer<T> = (T) -> Unit

object Util {
    val accountMap: MutableMap<String, MutableList<Song>> = hashMapOf()
    val recommendationMap: MutableMap<UUID, MutableList<String>> = hashMapOf()
    fun Context.toast(resId: Int, vararg args: String) = Toast.makeText(this, getString(resId, *args), Toast.LENGTH_SHORT).show()
    lateinit var editingSong: Song

    val songList
        get() = accountMap.values.flatten().sortedByDescending {
            it.recommendation
        }.toList()
    fun findOwner(id: UUID) : String? {
        for ((owner, songs) in accountMap.entries) {
            if (songs.count { song -> song.id == id } != 0) {
                return owner
            }
        }
        return null
    }

}
