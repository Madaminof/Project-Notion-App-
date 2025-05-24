package com.example.notesapp.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "daily_notes")  // jadval nomi
data class DailyNote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content:String,
    val date: String

)