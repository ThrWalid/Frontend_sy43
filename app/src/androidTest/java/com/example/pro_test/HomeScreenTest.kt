package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showPersonalDialogTest(){
        composeTestRule.setContent {
            HomeScreen(
                onLogout = {},
                onNavigateToContacts = {},
                onNavigateToGroups = {},
                onNavigateToTasks = {},
                onNavigateToSchedule = {}
            )
        }

        composeTestRule.onNodeWithText("Personal Events").performClick()
        composeTestRule.onNodeWithText("New Personal Task").assertExists()
    }
}