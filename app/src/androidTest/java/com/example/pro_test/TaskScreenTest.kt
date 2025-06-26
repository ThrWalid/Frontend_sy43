package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pro_test.data.local.Task
import com.example.pro_test.data.local.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskScreenTest {
    private lateinit var fakeRepository: TaskRepository
    private lateinit var viewModel: ApplicationViewModel

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())

        fakeRepository = FakeTaskRepository()
        viewModel = ApplicationViewModel(fakeRepository)
    }

    @Test
    fun NewTaskDialogTest() {
        composeTestRule.setContent {
            TasksScreen(
                viewModel = viewModel,
                onBack = {},
                onNavigateToHome = {},
                onNavigateToContacts = {},
                onNavigateToGroups = {},
                onNavigateToSchedule = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Add Task").performClick()
        composeTestRule.onNodeWithText("Add a New Task").assertIsDisplayed()
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