package com.example.notesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_notes")
data class DailyNote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val date: String, // LocalDateTime o‘rniga String ko‘rinishida saqlab, uni qayta parse qilasiz

    val isFavorite: Boolean = false,
    val isDeleted: Boolean = false
)
