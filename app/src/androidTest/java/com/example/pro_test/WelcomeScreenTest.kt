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
class WelcomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun buttonTriggeredTest(){
        var wasClicked = false

        composeTestRule.setContent {
            WelcomeScreen (
                onGetStartedClick = { wasClicked = true }
            )
        }
        composeTestRule.onNodeWithText("Get Started").performClick()

        assert(wasClicked)
    }
}