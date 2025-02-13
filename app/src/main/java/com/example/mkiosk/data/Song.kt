package com.example.mkiosk.data

import java.util.UUID

data class Song(var artist: String, var title: String, var recommendation: Int, val id: UUID)
