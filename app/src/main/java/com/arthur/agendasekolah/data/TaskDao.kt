package com.arthur.agendasekolah.data

import androidx.room.*
import com.arthur.agendasekolah.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task_table ORDER BY deadline ASC")
    fun getAll(): Flow<List<Task>>
}
