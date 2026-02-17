package com.example.jikananimeexplorer.data.local.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenres(genres: List<String>): String {
        return genres.joinToString(",")
    }

    @TypeConverter
    fun toGenres(genres: String): List<String> {
        if (genres.isEmpty()) return emptyList()
        return genres.split(",")
    }
}