package com.arthur.agendasekolah.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arthur.agendasekolah.model.Task
import com.arthur.agendasekolah.viewmodel.TaskViewModel
import com.arthur.agendasekolah.viewmodel.TaskViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskScreen() {
    val context = LocalContext.current
    val viewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(context.applicationContext as Application)
    )

    val taskList by viewModel.taskList.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Daftar Tugas", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(taskList, key = { it.id }) { task ->
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
                                Text("Judul: ${task.title}")
                                Text("Deskripsi: ${task.description}")
                                Text("Deadline: ${formatDate(task.deadline)}")
                            }

                            IconButton(onClick = { viewModel.deleteTask(task) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Hapus")
                            }
                        }
                    }
                }
            }

            if (showDialog) {
                AddTaskDialog(
                    onDismiss = { showDialog = false },
                    onSave = { title, description, deadline ->
                        viewModel.addTask(Task(title = title, description = description, deadline = deadline))
                        showDialog = false
                    }
                )
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
