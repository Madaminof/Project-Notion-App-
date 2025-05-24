package com.example.notesapp.uii.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.notesapp.viewmodel.DailyViewModel
import com.example.notesapp.uii.components.NoteCard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: DailyViewModel,
    onEditNote: (Int) -> Unit,
    onNoteClick: (Int) -> Unit  // qo'shildi
) {
    val notes by viewModel.notes.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    val filteredNotes = if (searchQuery.isBlank()) {
        notes
    } else {
        notes.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.content.contains(searchQuery, ignoreCase = true)
        }
    }

    val sortedNotes = filteredNotes.sortedByDescending { it.date }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchActive) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Izlash...") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = "Kundaliklar",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { isSearchActive = !isSearchActive }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEditNote(-1) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(sortedNotes) { note ->
                NoteCard(
                    note = note,
                    onClick = { onNoteClick(note.id) },  // bosilganda detailga o'tadi
                    onDeleteClick = { viewModel.deleteNote(note) }
                )
            }
        }
    }
}
