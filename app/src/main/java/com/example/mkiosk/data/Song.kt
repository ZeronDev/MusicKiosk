package com.example.mkiosk.data

import kotlinx.serialization.Serializable

@Serializable
data class Song(var artist: String, var title: String, var recommendation: Int, val id: String)
