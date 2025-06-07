package com.arthur.agendasekolah.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.arthur.agendasekolah.data.AppDatabase
import com.arthur.agendasekolah.data.ScheduleRepository
import com.arthur.agendasekolah.model.Schedule
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ScheduleRepository

    val scheduleList: LiveData<List<Schedule>>

    init {
        val dao = AppDatabase.getDatabase(application).scheduleDao()
        repository = ScheduleRepository(dao)
        scheduleList = repository.allSchedules.asLiveData()
    }

    fun addSchedule(schedule: Schedule) {
        viewModelScope.launch {
            repository.insert(schedule)
        }
    }
}
