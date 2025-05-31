package com.example.notesapp.Categories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp.viewmodel.DailyViewModel
import com.example.notesapp.uii.components.NoteCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeletedNotesScreen(viewModel: DailyViewModel, onNoteClick: (Int) -> Unit) {
    val notes by viewModel.notes.collectAsState()
    val deletedNotes = notes.filter { it.isDeleted }

    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
    val displayDate = today.format(formatter)

    Column(modifier = Modifier.fillMaxSize()) {

        // ✅ YANGILANGAN HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "O‘chirilgan eslatmalar",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Bu yerda o‘chirilgan eslatmalar saqlanadi",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = displayDate,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                )
            }
        }

        // 🧮 COUNT MATNI
        Text(
            text = "O‘chirilganlar soni: ${deletedNotes.size}",
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )

        // 🗑️ NOTE LIST
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(deletedNotes) { note ->
                var visible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(animationSpec = tween(500))
                ) {
                    NoteCard(
                        note = note,
                        onClick = { onNoteClick(note.id) },
                        onDeleteClick = {
                            viewModel.permanentlyDeleteNote(note)
                        }
                    )
                }
            }
        }
    }
}
