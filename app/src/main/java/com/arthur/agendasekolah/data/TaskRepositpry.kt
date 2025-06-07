package com.arthur.agendasekolah.data

import com.arthur.agendasekolah.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    val allTasks: Flow<List<Task>> = dao.getAll()

    suspend fun insert(task: Task) = dao.insert(task)
    suspend fun delete(task: Task) = dao.delete(task)
}
