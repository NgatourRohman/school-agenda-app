package com.arthur.agendasekolah.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.FilterList
import com.arthur.agendasekolah.model.Exam
import com.arthur.agendasekolah.viewmodel.ExamViewModel
import com.arthur.agendasekolah.viewmodel.ExamViewModelFactory

@Composable
fun ExamScreen() {
    val context = LocalContext.current
    val viewModel: ExamViewModel = viewModel(
        factory = ExamViewModelFactory(context.applicationContext as Application)
    )

    val examList by viewModel.examList.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var showUpcomingOnly by remember { mutableStateOf(true) }

    val currentTime = System.currentTimeMillis()
    val filteredExamList = if (showUpcomingOnly) {
        examList.filter { it.dateMillis > currentTime }
    } else {
        examList
    }

    val sortedExamList = filteredExamList.sortedBy { it.dateMillis }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Ujian") },
                actions = {
                    IconButton(onClick = { showUpcomingOnly = !showUpcomingOnly }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn {
                items(sortedExamList, key = { it.id }) { exam ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Ujian: ${exam.title}")
                                Text("Tanggal: ${formatDate(exam.dateMillis)}")
                                Text("Hari tersisa: ${countdownDays(exam.dateMillis)} hari")
                            }

                            IconButton(onClick = { viewModel.deleteExam(exam) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Hapus")
                            }
                        }
                    }
                }
            }

            if (showDialog) {
                AddExamDialog(
                    onDismiss = { showDialog = false },
                    onSave = { title, dateMillis ->
                        viewModel.addExam(Exam(title = title, dateMillis = dateMillis))
                        showDialog = false
                    }
                )
            }
        }
    }
}

fun countdownDays(dateMillis: Long): Long {
    val currentTime = System.currentTimeMillis()
    return (dateMillis - currentTime) / (1000 * 60 * 60 * 24)
}
