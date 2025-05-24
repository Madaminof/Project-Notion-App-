package com.example.notesapp.data.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.data.model.DailyNote


@Dao
interface DailyNoteDao{
    @Insert
    suspend fun insert(note: DailyNote)
    @Update
    suspend fun update(note: DailyNote)
    @Delete
    suspend fun delete(note: DailyNote)


    @Query("SELECT * FROM daily_notes")
    suspend fun getAllNotes(): List<DailyNote> // Barcha kundaliklarni olish

    @Query("SELECT * FROM daily_notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): DailyNote? // ID orqali bitta kundalikni olish
}