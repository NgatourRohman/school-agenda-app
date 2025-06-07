package com.arthur.agendasekolah.ui.screens

import androidx.compose.material.*
import androidx.compose.runtime.*
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
                    label = { Text("Mata Pelajaran") }
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
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