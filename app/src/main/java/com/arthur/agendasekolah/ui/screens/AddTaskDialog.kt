package com.arthur.agendasekolah.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, Long) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var deadlineMillis by remember { mutableStateOf<Long?>(null) }
    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    // Date Picker
    val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, monthOfYear, dayOfMonth)
            selectedDate = "${dayOfMonth}/${monthOfYear + 1}/$year"
            deadlineMillis = selectedCalendar.timeInMillis
        }

    // Time Picker
    val timeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            selectedTime = "$hourOfDay:$minute"
            val timeInMillis = deadlineMillis?.let { it + (hourOfDay * 3600000) + (minute * 60000) }
            deadlineMillis = timeInMillis
        }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Tugas") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Deskripsi") }
                )

                // Tanggal Deadline
                TextButton(onClick = {
                    val datePicker = DatePickerDialog(
                        context,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePicker.show()
                }) {
                    Text("Pilih Tanggal Deadline: $selectedDate")
                }

                // Waktu Deadline
                TextButton(onClick = {
                    val timePicker = TimePickerDialog(
                        context,
                        timeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    timePicker.show()
                }) {
                    Text("Pilih Waktu Deadline: $selectedTime")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && description.isNotBlank() && deadlineMillis != null) {
                    onSave(title, description, deadlineMillis!!)
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
