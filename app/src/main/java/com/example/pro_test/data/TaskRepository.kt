package com.example.pro_test.data

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasksStream(): Flow<List<Task>>
    fun getTaskStream(id: Int): Flow<Task>
    fun deleteAllTasks()

    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
}