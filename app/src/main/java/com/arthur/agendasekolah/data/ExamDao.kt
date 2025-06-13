package com.arthur.agendasekolah.data

import androidx.room.*
import com.arthur.agendasekolah.model.Exam
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exam: Exam)

    @Delete
    suspend fun delete(exam: Exam)

    @Query("SELECT * FROM exam_table ORDER BY dateMillis ASC")
    fun getAll(): Flow<List<Exam>>
}
