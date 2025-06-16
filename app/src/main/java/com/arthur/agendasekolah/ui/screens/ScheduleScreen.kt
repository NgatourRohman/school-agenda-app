package com.arthur.agendasekolah.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arthur.agendasekolah.model.Schedule
import com.arthur.agendasekolah.viewmodel.ScheduleViewModel
import com.arthur.agendasekolah.viewmodel.ScheduleViewModelFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

@Composable
fun ScheduleScreen() {
    val context = LocalContext.current
    val viewModel: ScheduleViewModel = viewModel(
        factory = ScheduleViewModelFactory(context.applicationContext as Application)
    )

    val scheduleList by viewModel.scheduleList.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    // Filter active schedules
    var showActiveSchedulesOnly by remember { mutableStateOf(false) }

    val filteredScheduleList = if (showActiveSchedulesOnly) {
        scheduleList.filter { it.subject.isNotEmpty() }
    } else {
        scheduleList
    }

    val sortedScheduleList = filteredScheduleList.sortedBy { it.day }  // Sort by day or time

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Jadwal Pelajaran") },
                actions = {
                    // Filter toggle
                    IconButton(onClick = { showActiveSchedulesOnly = !showActiveSchedulesOnly }) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filter")
                    }
                    // Sorting toggle
                    IconButton(onClick = { /* Sort logic */ }) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                    }
                }
            )
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
                items(sortedScheduleList, key = { it.id }) { item ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                    ) {
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
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus Jadwal")
                                }
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
