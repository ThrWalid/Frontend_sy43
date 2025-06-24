package com.example.pro_test

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro_test.data.local.Task
import com.example.pro_test.data.local.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ApplicationViewModel(val taskRepository: TaskRepository): ViewModel() {

    private val _taskList = mutableStateOf<List<Task>>(emptyList())
    val taskList: State<List<Task>> = _taskList

    init {
        viewModelScope.launch {
            taskRepository.getAllTasksStream().collect { tasks ->
                _taskList.value = tasks
            }

            // Initial mockup data for testing the application
            if(taskList.value.isEmpty()) {
                addTask(
                    Task(
                        title = "Design 2 App Screens",                     // Task title
                        subtitle = "Need to design for citter",             // Brief description
                        date = "Mon, 10 July 2025",                         // Due date
                        done = true                                         // Completion status
                    )
                )
                addTask(
                    Task(
                        title = "Design 1 Website",
                        subtitle = "Need to design for citter",
                        date = "Tue, 23 July 2025",
                        done = true
                    )
                )
                addTask(
                    Task(
                        title = "Data-Base",
                        subtitle = "Need to design for citter",
                        date = "Sat, 14 July 2026",
                        done = false
                    )
                )
            }
            // * Removed 'members' property from Task objects
        }
    }

    fun getTaskList(){
        viewModelScope.launch {
            taskRepository.getAllTasksStream().collect { tasks ->
                _taskList.value = tasks            }
        }
    }

    suspend fun getTask(id: Int): Task {
        return taskRepository.getTaskStream(id).first()
    }

    fun removeAllTasks(){
        viewModelScope.launch{
            taskRepository.deleteAllTasks()
        }
    }

    fun removeTask(task: Task){
        viewModelScope.launch{
            taskRepository.deleteTask(task)
        }
    }

    fun modifyTask(task: Task){
        viewModelScope.launch{
            taskRepository.updateTask(task)
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch{
            taskRepository.insertTask(task)
        }
    }
}