package com.arthur.agendasekolah.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Jadwal(
    val lesson: String,
    val hour: String,
    val day: String
)

@Composable
fun ScheduleScreen() {
    var showDialog by remember { mutableStateOf(false) }
    val jadwalList = remember { mutableStateListOf<Jadwal>() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp)) {

            Text("Jadwal Pelajaran", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(jadwalList) { item ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Pelajaran: ${item.lesson}")
                            Text("Jam: ${item.hour}")
                            Text("Hari: ${item.day}")
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AddScheduleDialog(
                onDismiss = { showDialog = false },
                onSave = { lesson, hour, day ->
                    jadwalList.add(Jadwal(lesson, hour, day))
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddScheduleDialog(
    onDismiss: () -> Unit,
    onSave: (lesson: String, hour: String, day: String) -> Unit
) {
    var lesson by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Jadwal") },
        text = {
            Column {
                OutlinedTextField(
                    value = lesson,
                    onValueChange = { lesson = it },
                    label = { Text("Mata Pelajaran") }
                )
                OutlinedTextField(
                    value = hour,
                    onValueChange = { hour = it },
                    label = { Text("Jam") }
                )
                OutlinedTextField(
                    value = day,
                    onValueChange = { day = it },
                    label = { Text("Hari (Senin, Selasa...)") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (lesson.isNotBlank() && hour.isNotBlank() && day.isNotBlank()) {
                    onSave(lesson, hour, day)
                }
            }) {
                Text("Simpan")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
