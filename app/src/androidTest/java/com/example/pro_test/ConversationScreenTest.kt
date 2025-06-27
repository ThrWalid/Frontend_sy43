package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displayMessageTest(){
        val testMessage = "Hello, Alice!"

        composeTestRule.setContent {
            ConversationScreen(
                contactName = "Alice",
                onBack = {}
            )
        }

        composeTestRule.onNodeWithText("Écrire un message")
            .performTextInput(testMessage)

        // Click the send button
        composeTestRule.onNodeWithContentDescription("Send")
            .performClick()

        // Assert that the message now appears on the screen
        composeTestRule.onNodeWithText(testMessage)
            .assertIsDisplayed()
    }
}