package com.arthur.agendasekolah.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.arthur.agendasekolah.data.AppDatabase
import com.arthur.agendasekolah.data.TaskRepository
import com.arthur.agendasekolah.model.Task
import com.arthur.agendasekolah.notifications.ReminderWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val taskList: LiveData<List<Task>>

    init {
        val dao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(dao)
        taskList = repository.allTasks.asLiveData()
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
            scheduleTaskReminder(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun markTaskAsCompleted(task: Task) {
        val updatedTask = task.copy(isCompleted = true)
        viewModelScope.launch {
            repository.update(updatedTask)
        }
    }

    fun scheduleTaskReminder(task: Task) {
        val reminderTime = task.deadline - 24 * 60 * 60 * 1000  // 1 day before

        val data = workDataOf(
            "title" to task.title,
            "description" to "Ingatkan saya tentang tugas: ${task.title}"
        )

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(reminderTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(getApplication()).enqueue(workRequest)
    }
}
