package com.arthur.agendasekolah.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val time: String,
    val day: String
)