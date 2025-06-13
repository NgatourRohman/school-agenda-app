package com.arthur.agendasekolah.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arthur.agendasekolah.model.Schedule
import com.arthur.agendasekolah.viewmodel.ScheduleViewModel
import com.arthur.agendasekolah.viewmodel.ScheduleViewModelFactory

@Composable
fun ScheduleScreen() {
    val context = LocalContext.current
    val viewModel: ScheduleViewModel = viewModel(
        factory = ScheduleViewModelFactory(context.applicationContext as Application)
    )

    val scheduleList by viewModel.scheduleList.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Jadwal Pelajaran", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(scheduleList, key = { it.id }) { item ->
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
                                Text("Pelajaran: ${item.subject}")
                                Text("Jam: ${item.time}")
                                Text("Hari: ${item.day}")
                            }

                            IconButton(onClick = { viewModel.deleteSchedule(item) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Hapus Jadwal"
                                )
                            }
                        }
                    }
                }
            }

            if (showDialog) {
                AddScheduleDialog(
                    onDismiss = { showDialog = false },
                    onSave = { subject, time, day ->
                        viewModel.addSchedule(Schedule(subject = subject, time = time, day = day))
                        showDialog = false
                    }
                )
            }
        }
    }
}