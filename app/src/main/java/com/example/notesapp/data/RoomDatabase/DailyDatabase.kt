package com.example.notesapp.data.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.data.DAO.DailyNoteDao
import com.example.notesapp.data.model.DailyNote

@Database(entities = [DailyNote::class], version = 2)
abstract class DailyDatabase : RoomDatabase() {

    abstract fun dailyNoteDao(): DailyNoteDao

    companion object {
        @Volatile
        private var INSTANCE: DailyDatabase? = null

        fun getDatabase(context: Context): DailyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DailyDatabase::class.java,
                    "daily_database"
                )
                    .fallbackToDestructiveMigration()  // Bu qator qo'shildi
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
