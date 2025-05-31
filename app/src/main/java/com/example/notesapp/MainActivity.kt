package com.example.notesapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.data.RoomDatabase.DailyDatabase
import com.example.notesapp.data.repository.DailyRepository
import com.example.notesapp.navigation.NavGraph
import com.example.notesapp.viewmodel.DailyViewModel

class MainActivity : ComponentActivity() {

    private val database by lazy {
        DailyDatabase.getDatabase(applicationContext)
    }

    private val dailyRepository by lazy {
        DailyRepository(dailyNoteDao = database.dailyNoteDao())
    }

    private val dailyViewModel: DailyViewModel by viewModels {
        DailyViewModel.Factory(dailyRepository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    viewModel = dailyViewModel
                )
            }
        }
    }

}
