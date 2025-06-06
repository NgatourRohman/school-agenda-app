package com.arthur.agendasekolah.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arthur.agendasekolah.ui.screens.*

@Composable
fun MainScreen() {
    var selected by remember { mutableStateOf(0) }

    val screens = listOf("Jadwal", "Tugas", "Ujian")

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                    label = { Text("Jadwal") },
                    selected = selected == 0,
                    onClick = { selected = 0 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Tugas") },
                    selected = selected == 1,
                    onClick = { selected = 1 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Timer, contentDescription = null) },
                    label = { Text("Ujian") },
                    selected = selected == 2,
                    onClick = { selected = 2 }
                )
            }
        }
    ) {
        when (selected) {
            0 -> JadwalScreen()
            1 -> TugasScreen()
            2 -> UjianScreen()
        }
    }
}
