package com.arthur.agendasekolah.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_table")
data class Exam(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val dateMillis: Long
)
