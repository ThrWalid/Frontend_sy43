package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class GroupsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun QueryTextFieldTest(){
        val query = "Alpha"

        composeTestRule.setContent {
            GroupsScreen(
                navController = rememberNavController(),
                onNavigateToHome = {},
                onNavigateToContacts = {},
                onNavigateToTasks = {},
                onNavigateToSchedule = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Search").performClick()
        composeTestRule.onNodeWithText(query).assertExists()
        composeTestRule.onNodeWithText("Project Alpha").assertIsDisplayed()
    }
}