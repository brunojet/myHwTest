package com.example.myhwtest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test


class MainActivityUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun greetingDisplaysCorrectInformation() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Hello Android!").assertIsDisplayed()
        composeTestRule.onNodeWithText("getManufacturer: SMART").assertIsDisplayed()
        composeTestRule.onNodeWithText("serialNumber: 02:00:00:00:00:00").assertIsDisplayed()
    }
}