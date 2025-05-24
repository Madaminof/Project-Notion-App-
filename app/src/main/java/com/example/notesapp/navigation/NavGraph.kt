package com.example.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapp.uii.add_edit.AddEditScreen
import com.example.notesapp.uii.home.DetailScreen
import com.example.notesapp.uii.home.HomeScreen
import com.example.notesapp.viewmodel.DailyViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: DailyViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onEditNote = { noteId ->
                    navController.navigate("addEdit/$noteId")
                },
                onNoteClick = { noteId ->
                    navController.navigate("detail/$noteId")
                }
            )
        }
        composable("addEdit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: -1

            AddEditScreen(
                noteId = noteId,
                viewModel = viewModel,
                onSave = {
                    navController.popBackStack()
                }
            )
        }
        composable("detail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: -1
            val note = viewModel.notes.collectAsState().value.find { it.id == noteId }

            note?.let {
                DetailScreen(
                    note = it,
                    onEditClick = {
                        navController.navigate("addEdit/$noteId")
                    },
                    onDeleteClick = {
                        viewModel.deleteNote(it)
                        navController.popBackStack("home", inclusive = false)
                    }
                )
            }
        }
    }
}
