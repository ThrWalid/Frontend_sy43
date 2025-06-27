package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditEventScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun nameTextFieldTest(){
        val name = "Test Example"

        composeTestRule.setContent {
            EditEventScreen(
                onCancel = {},
                onSave = {}
            )
        }

        val nameNode = composeTestRule.onNodeWithText("Name")

        nameNode.performTextInput(name)
        nameNode.assertTextContains(name)
    }
}