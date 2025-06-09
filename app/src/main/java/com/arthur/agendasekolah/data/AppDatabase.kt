package com.arthur.agendasekolah.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arthur.agendasekolah.model.Exam
import com.arthur.agendasekolah.model.Schedule
import com.arthur.agendasekolah.model.Task

@Database(entities = [Schedule::class, Task::class, Exam::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun taskDao(): TaskDao
    abstract fun ExamDao(): ExamDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agenda_database"
                )
                    .fallbackToDestructiveMigration() // ⬅ Tambahkan ini
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}