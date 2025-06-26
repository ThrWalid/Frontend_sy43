package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScheduleScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun NewEventDialogTest() {
        composeTestRule.setContent {
            ScheduleScreen(
                onBack = {},
                onNavigateToGroups = {},
                onNavigateToContacts = {},
                onNavigateToHome = {},
                onNavigateToTasks = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Add Event").performClick()
        composeTestRule.onNodeWithText("Save")
    }
}