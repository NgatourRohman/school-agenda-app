package com.arthur.agendasekolah.ui.screens

import android.app.DatePickerDialog
import android.os.Build
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun AddExamDialog(
    onDismiss: () -> Unit,
    onSave: (String, Long) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var examDateMillis by remember { mutableStateOf<Long?>(null) }
    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    // Date Picker for Exam Date
    val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, monthOfYear, dayOfMonth)
            selectedDate = "${dayOfMonth}/${monthOfYear + 1}/$year"
            examDateMillis = selectedCalendar.timeInMillis
        }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Ujian") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Ujian") }
                )

                // Tanggal Ujian
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
                    Text("Pilih Tanggal Ujian: $selectedDate")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && examDateMillis != null) {
                    onSave(title, examDateMillis!!)
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
