package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.model.DailyNote
import com.example.notesapp.data.repository.DailyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DailyViewModel(private val repository: DailyRepository) : ViewModel() {

    private val _notes = MutableStateFlow<List<DailyNote>>(emptyList())
    val notes: StateFlow<List<DailyNote>> get() = _notes

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            _notes.value = repository.getAllNotes()
        }
    }

    fun permanentlyDeleteNote(note: DailyNote) {
        viewModelScope.launch {
            repository.deleteNote(note)  // To'liq o'chirish
            loadNotes()
        }
    }



    fun addNote(note: DailyNote) {
        viewModelScope.launch {
            repository.insertNote(note)
            loadNotes()
        }
    }

    fun deleteNote(note: DailyNote) {
        viewModelScope.launch {
            repository.deleteNote(note)
            loadNotes()
        }
    }

    // Yangilangan funksiya
    fun deleteNoteById(id: Int) {
        viewModelScope.launch {
            val note = _notes.value.find { it.id == id }
            note?.let {
                repository.deleteNote(it)
                loadNotes()
            }
        }
    }

    fun updateNote(note: DailyNote) {
        viewModelScope.launch {
            repository.updateNote(note)
            loadNotes()
        }
    }

    suspend fun getNoteById(id: Int): DailyNote? {
        return repository.getNoteById(id)
    }

    class Factory(private val repository: DailyRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DailyViewModel(repository) as T
        }
    }
}
