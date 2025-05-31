package com.example.notesapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapp.uii.add_edit.AddEditScreen
import com.example.notesapp.uii.home.DetailScreen
import com.example.notesapp.uii.home.HomeScreen
import com.example.notesapp.viewmodel.DailyViewModel
import com.example.notesapp.Categories.AllNotesScreen
import com.example.notesapp.Categories.SettingsScreen
import com.example.notesapp.Categories.TodayNotesScreen
import com.example.notesapp.Categories.DeletedNotesScreen
import com.example.notesapp.Categories.FavoriteNotesScreen

@RequiresApi(Build.VERSION_CODES.O)
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
                },
                onDeleteNote = { noteId ->
                    viewModel.deleteNoteById(noteId)
                },
                onAllNotesClick = {
                    navController.navigate("all_notes")
                },
                onTodayNotesClick = {
                    navController.navigate("today_notes")
                },
                onFavoriteNotesClick = {
                    navController.navigate("favorite_notes")
                },
                onDeletedNotesClick = {
                    navController.navigate("deleted_notes")
                },
                onSettingsClick = {
                    navController.navigate("settings")
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

        composable("addEdit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: -1
            AddEditScreen(
                noteId = noteId,
                viewModel = viewModel,
                onSave = {
                    // Saqlagandan keyin home ga qaytish
                    navController.popBackStack("home", inclusive = false)
                }
            )
        }


        composable("all_notes") {
            AllNotesScreen(
                viewModel = viewModel,
                onNoteClick = { noteId ->
                    navController.navigate("detail/$noteId")
                }
            )
        }

        composable("today_notes") {
            TodayNotesScreen(
                viewModel = viewModel,
                onNoteClick = { noteId ->
                    navController.navigate("detail/$noteId")
                }
            )
        }

        composable("favorite_notes") {
            FavoriteNotesScreen(
                viewModel = viewModel,
                onNoteClick = { noteId ->
                    navController.navigate("detail/$noteId")
                }
            )
        }

        composable("deleted_notes") {
            DeletedNotesScreen(
                viewModel = viewModel,
                onNoteClick = { noteId ->
                    navController.navigate("detail/$noteId")
                }
            )
        }

        composable("settings") {
            SettingsScreen()
        }
    }
}
