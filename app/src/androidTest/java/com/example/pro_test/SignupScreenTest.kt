package com.example.pro_test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignupScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun matchingPasswordErrorTest(){
        val fullName = "Test Example"
        val email = "test@example.com"
        val password = "test_example"
        val incorrect_password = "example_test"


        composeTestRule.setContent {
            SignUpScreen(
                onSignUpSuccess = {},
                onNavigateBack = {}
            )
        }

        val fullNameNode = composeTestRule.onNodeWithText("Full Name")
        val emailNode = composeTestRule.onNodeWithText("Email")
        val passwordNode = composeTestRule.onNodeWithText("Password")
        val confirmPasswordNode = composeTestRule.onNodeWithText("Confirm Password")

        fullNameNode.performTextInput(fullName)
        emailNode.performTextInput(email)
        passwordNode.performTextInput(password)
        confirmPasswordNode.performTextInput(incorrect_password)

        composeTestRule.onNodeWithText("Passwords do not match").assertExists()
    }


}