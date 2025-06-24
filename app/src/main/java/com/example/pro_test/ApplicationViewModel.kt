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
            getTaskList()
        }
    }

    fun removeTask(task: Task){
        viewModelScope.launch{
            taskRepository.deleteTask(task)
            getTaskList()
        }
    }

    fun modifyTask(task: Task){
        viewModelScope.launch{
            taskRepository.updateTask(task)
            getTaskList()
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch{
            taskRepository.insertTask(task)
            getTaskList()
        }
    }
}