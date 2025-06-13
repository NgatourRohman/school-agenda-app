package com.arthur.agendasekolah.data

import androidx.room.*
import com.arthur.agendasekolah.model.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: Schedule)

    @Query("SELECT * FROM schedule_table ORDER BY day ASC")
    fun getAll(): Flow<List<Schedule>>

    @Delete
    suspend fun delete(schedule: Schedule)
}