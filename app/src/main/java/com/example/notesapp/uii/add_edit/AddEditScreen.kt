package com.example.notesapp.uii.add_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.notesapp.data.model.DailyNote
import com.example.notesapp.viewmodel.DailyViewModel
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

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // HEADER qismini yangilangan versiyasi
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.primary) // biroz och rang
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = if (noteId == -1) "➕ Yangi Eslatma" else "✏️ Eslatmani Tahrirlash",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "📓 NotesApp ilovasida eslatmalaringizni boshqaring",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }



            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top // SpaceBetween o‘rniga Top qo‘ying
            ) {
                OutlinedTextField(
                    value = noteTitle.value,
                    onValueChange = { noteTitle.value = it },
                    label = { Text("Sarlavha") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = noteContent.value,
                    onValueChange = { noteContent.value = it },
                    label = { Text("Kontent") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10
                )

                Spacer(modifier = Modifier.height(24.dp)) // Tugma va content orasini biroz bo‘shatish uchun

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
                    modifier = Modifier.align(Alignment.End),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Saqlash")
                }
            }

        }
    }
}

fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}
