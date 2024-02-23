package com.example.newsapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsapp.ui.screens.webview.WebViewScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebViewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun uiComponentTest_assertExists(){
//        composeTestRule.setContent {
////            WebViewScreen()
//        }
//        composeTestRule.onNodeWithText("https://www.nytimes.com/2024/02/21/us/politics/cybersecurity-ports.html").assertExists()
//    }
//    @Test
//    fun uiComponentTest_assertIsDisplayed(){
//        composeTestRule.setContent {
////            WebViewScreen()
//        }
//        composeTestRule.onNodeWithText("https://www.nytimes.com/2024/02/21/us/politics/cybersecurity-ports.html").assertIsDisplayed()
//    }
}