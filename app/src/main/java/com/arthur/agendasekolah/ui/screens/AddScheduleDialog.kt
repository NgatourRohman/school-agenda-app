package com.arthur.agendasekolah.ui.screens

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*

@Composable
fun AddScheduleDialog(
    onDismiss: () -> Unit,
    onSave: (subject: String, time: String, day: String) -> Unit
) {
    var subject by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Jadwal") },
        text = {
            Column {
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Mata Pelajaran") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Jam") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = day,
                    onValueChange = { day = it },
                    label = { Text("Hari") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (subject.isNotBlank() && time.isNotBlank() && day.isNotBlank()) {
                    onSave(subject, time, day)
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
