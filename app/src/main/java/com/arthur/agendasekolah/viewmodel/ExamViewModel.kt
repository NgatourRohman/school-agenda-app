package com.arthur.agendasekolah.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.arthur.agendasekolah.data.AppDatabase
import com.arthur.agendasekolah.data.ExamRepository
import com.arthur.agendasekolah.model.Exam
import com.arthur.agendasekolah.notifications.ReminderWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ExamViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExamRepository
    val examList: LiveData<List<Exam>>

    init {
        val dao = AppDatabase.getDatabase(application).ExamDao()
        repository = ExamRepository(dao)
        examList = repository.allExams.asLiveData()
    }

    fun addExam(exam: Exam) {
        viewModelScope.launch {
            repository.insert(exam)
            scheduleExamReminder(exam)
        }
    }

    fun deleteExam(exam: Exam) {
        viewModelScope.launch {
            repository.delete(exam)
        }
    }

    fun scheduleExamReminder(exam: Exam) {
        val reminderTime = exam.dateMillis - 24 * 60 * 60 * 1000  // 1 day before

        val data = workDataOf(
            "title" to exam.title,
            "description" to "Ingatkan saya tentang ujian: ${exam.title}"
        )

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(reminderTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(getApplication()).enqueue(workRequest)
    }
}
