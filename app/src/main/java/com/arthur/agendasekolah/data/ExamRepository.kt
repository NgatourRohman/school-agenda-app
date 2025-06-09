package com.arthur.agendasekolah.data

import com.arthur.agendasekolah.model.Exam
import kotlinx.coroutines.flow.Flow

class ExamRepository(private val dao: ExamDao) {
    val allExams: Flow<List<Exam>> = dao.getAll()

    suspend fun insert(exam: Exam) = dao.insert(exam)
    suspend fun delete(exam: Exam) = dao.delete(exam)
}
