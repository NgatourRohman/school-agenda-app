package com.arthur.agendasekolah.data

import com.arthur.agendasekolah.model.Schedule
import kotlinx.coroutines.flow.Flow

class ScheduleRepository(private val dao: ScheduleDao) {
    val allSchedules: Flow<List<Schedule>> = dao.getAll()

    suspend fun insert(schedule: Schedule) {
        dao.insert(schedule)
    }

    suspend fun delete(schedule: Schedule) {
        dao.delete(schedule)
    }
}