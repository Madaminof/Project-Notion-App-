package com.example.notesapp.data.repository

import com.example.notesapp.data.DAO.DailyNoteDao
import com.example.notesapp.data.model.DailyNote


class DailyRepository(private val dailyNoteDao: DailyNoteDao) {

    suspend fun insertNote(note: DailyNote) {
        dailyNoteDao.insert(note)
    }

    suspend fun updateNote(note: DailyNote) {
        dailyNoteDao.update(note)
    }

    suspend fun getAllNotes(): List<DailyNote> {
        return dailyNoteDao.getAllNotes()
    }

    suspend fun getNoteById(id: Int): DailyNote? {
        return dailyNoteDao.getNoteById(id)
    }
    suspend fun deleteNote(note: DailyNote) {
        dailyNoteDao.delete(note)
    }

}
