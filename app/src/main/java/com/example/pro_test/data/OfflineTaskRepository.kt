package com.example.pro_test.data

import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val taskDao: TaskDao): TaskRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()
    override fun getTaskStream(id: Int): Flow<Task> = taskDao.getTask(id)
    override fun deleteAllTasks() = taskDao.deleteAllTasks()

    override suspend fun insertTask(task: Task) = taskDao.insert(task)
    override suspend fun deleteTask(task: Task) = taskDao.delete(task)
    override suspend fun updateTask(task: Task) = taskDao.update(task)
}