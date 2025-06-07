package com.arthur.agendasekolah.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.arthur.agendasekolah.data.AppDatabase
import com.arthur.agendasekolah.data.TaskRepository
import com.arthur.agendasekolah.model.Task
import kotlinx.coroutines.launch

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
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}
