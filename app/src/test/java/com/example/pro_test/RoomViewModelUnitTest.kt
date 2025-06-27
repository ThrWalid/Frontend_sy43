package com.example.pro_test

import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import com.example.pro_test.data.local.Task
import com.example.pro_test.data.local.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.junit.After
import org.junit.Before
import org.junit.Test

class RoomViewModelUnitTest {
    private lateinit var fakeRepository: TaskRepository
    private lateinit var viewModel: ApplicationViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())

        fakeRepository = FakeTaskRepository()
        viewModel = ApplicationViewModel(fakeRepository)
    }

    @Test
    fun testAddTask() = runTest {
        val newTask = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )

        viewModel.addTask(newTask)
        advanceUntilIdle()

        assertEquals(listOf(newTask), viewModel.taskList.value)
    }

    @Test
    fun testModifyTask() = runTest{
        val newTask = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )

        viewModel.addTask(newTask)
        advanceUntilIdle()

        val modifiedTask = newTask.copy(done = true)
        viewModel.modifyTask(modifiedTask)
        advanceUntilIdle()

        val retrievedTask = viewModel.getTask(id = 1)
        advanceUntilIdle()

        assertEquals(modifiedTask, retrievedTask)
    }

    @Test
    fun testRemoveTask() = runTest {
        val newTask = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )

        viewModel.addTask(newTask)
        advanceUntilIdle()

        viewModel.removeTask(newTask)
        advanceUntilIdle()

        assertEquals(emptyList<Task>(), viewModel.taskList.value)
    }

    @Test
    fun testRemoveAllTasks() = runTest {
        val newTask1 = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )
        val newTask2 = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )

        viewModel.addTask(newTask1)
        viewModel.addTask(newTask2)
        advanceUntilIdle()

        viewModel.removeAllTasks()
        advanceUntilIdle()

        val taskReference = viewModel.taskList

        assertEquals(emptyList<Task>(), taskReference)
    }

    @Test
    fun testGetTask() = runTest {
        val newTask = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )

        viewModel.addTask(newTask.copy())
        advanceUntilIdle()

        val retrievedTask = viewModel.getTask(newTask.id)
        advanceUntilIdle()

        assertEquals(newTask, retrievedTask)
    }

    @Test
    fun testGetTaskList() = runTest {
        val newTask1 = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )
        val newTask2 = Task(
            id = 1,
            title = "Test Task",
            subtitle = "Subtitle",
            date = "05.10.2003",
            done = false
        )

        val taskList = listOf(newTask1, newTask2)

        viewModel.addTask(newTask1)
        viewModel.addTask(newTask2)
        advanceUntilIdle()

        viewModel.getTaskList()
        advanceUntilIdle()

        assertEquals(taskList, viewModel.taskList.value)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}

class FakeTaskRepository(): TaskRepository {
    private val tasks = MutableStateFlow<List<Task>>(emptyList())

    override fun getAllTasksStream(): Flow<List<Task>> = tasks

    override fun getTaskStream(id: Int): Flow<Task> =
    tasks.map { list -> list.first {it.id == id }}

    override fun deleteAllTasks(){
        tasks.value = emptyList()
    }

    override suspend fun insertTask(task: Task){
        tasks.value = tasks.value + task
    }

    override suspend fun deleteTask(task: Task) {
        tasks.value = tasks.value - task
    }
    override suspend fun updateTask(task: Task) {
        tasks.value = tasks.value.map { if (it.id == task.id) task else it }
    }
}