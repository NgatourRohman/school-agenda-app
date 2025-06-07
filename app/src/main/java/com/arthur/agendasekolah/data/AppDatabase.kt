package com.arthur.agendasekolah.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arthur.agendasekolah.model.Schedule

@Database(entities = [Schedule::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agenda_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}