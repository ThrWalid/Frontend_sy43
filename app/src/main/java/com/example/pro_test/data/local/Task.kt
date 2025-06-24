package com.example.pro_test.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate=true)
    val id: Int = 0,
    var title: String,
    var subtitle: String,
    var date: String,
    var done: Boolean
)