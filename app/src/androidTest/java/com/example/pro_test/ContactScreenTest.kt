package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun QueryTextFieldTest(){
        val query = "Marie"

        composeTestRule.setContent {
            ContactsScreen(
                navController = rememberNavController(),
                onBack = {},
                onNavigateToHome = {},
                onNavigateToGroups = {},
                onNavigateToTasks = {},
                onNavigateToSchedule = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Search").performClick()
        composeTestRule.onNodeWithText(query).assertExists()
        composeTestRule.onNodeWithText("Marie Martin").assertIsDisplayed()
    }
}