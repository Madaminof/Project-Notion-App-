package com.example.notesapp.uii.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp.data.model.DailyNote
import com.example.notesapp.viewmodel.DailyViewModel
import androidx.compose.ui.Alignment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddEditScreen(
    noteId: Int,
    viewModel: DailyViewModel,
    onSave: () -> Unit
) {
    val noteTitle = remember { mutableStateOf("") }
    val noteContent = remember { mutableStateOf("") }

    LaunchedEffect(noteId) {
        if (noteId != -1) {
            val note = viewModel.getNoteById(noteId)
            note?.let {
                noteTitle.value = it.title
                noteContent.value = it.content
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = noteTitle.value,
            onValueChange = { noteTitle.value = it },
            label = { Text("Sarlavha") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = noteContent.value,
            onValueChange = { noteContent.value = it },
            label = { Text("Kontent") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val currentDate = getCurrentDateTime()

                if (noteId == -1) {
                    viewModel.addNote(
                        DailyNote(
                            title = noteTitle.value,
                            content = noteContent.value,
                            date = currentDate
                        )
                    )
                } else {
                    viewModel.updateNote(
                        DailyNote(
                            id = noteId,
                            title = noteTitle.value,
                            content = noteContent.value,
                            date = currentDate
                        )
                    )
                }
                onSave()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Saqlash")
        }
    }
}

fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date())
}
