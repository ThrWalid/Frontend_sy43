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
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun emailTextFieldTest(){
        val email = "test@example.com"

        composeTestRule.setContent {
            ModernLoginScreen(
                onLoginSuccess = {},
                onNavigateToSignUp = {},
                onForgotPassword = {}
            )
        }

        val emailNode = composeTestRule.onNodeWithText("Email")

        emailNode.performTextInput(email)
        emailNode.assertTextContains(email)
    }

}