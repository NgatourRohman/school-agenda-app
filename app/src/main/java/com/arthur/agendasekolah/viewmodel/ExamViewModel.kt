package com.arthur.agendasekolah.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.arthur.agendasekolah.data.AppDatabase
import com.arthur.agendasekolah.data.ExamRepository
import com.arthur.agendasekolah.model.Exam
import kotlinx.coroutines.launch

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
        }
    }

    fun deleteExam(exam: Exam) {
        viewModelScope.launch {
            repository.delete(exam)
        }
    }
}
