package com.example.gituserapp.ui.screen

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import com.example.gituserapp.MainActivity
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.test.onRoot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun navigateToDetail_displaysUserInfo_scrollRepos_and_opensBrowser() {
        composeTestRule.onNodeWithText("ユーザーを検索…").performTextInput("google")
        
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("google", substring = true, ignoreCase = true, useUnmergedTree = true).assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }

        composeTestRule.onNodeWithText("google", substring = true, ignoreCase = true, useUnmergedTree = true).performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("Google", substring = true, ignoreCase = true, useUnmergedTree = true).assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }
        composeTestRule.onNodeWithText("Google", substring = true, ignoreCase = true, useUnmergedTree = true).assertIsDisplayed()
        
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("リポジトリを検索", substring = true, ignoreCase = true, useUnmergedTree = true).assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }
        composeTestRule.onNodeWithText("リポジトリを検索", substring = true, ignoreCase = true, useUnmergedTree = true).assertIsDisplayed()
        
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("guava", substring = true, ignoreCase = true, useUnmergedTree = true).assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }
        composeTestRule.onNodeWithText("guava", substring = true, ignoreCase = true, useUnmergedTree = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("guava", substring = true, ignoreCase = true, useUnmergedTree = true).performClick()
        Intents.intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData("https://github.com/google/guava")
            )
        )

        composeTestRule.onRoot().performTouchInput { swipeUp() }
        
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("protobuf", substring = true, ignoreCase = true, useUnmergedTree = true).assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }
        composeTestRule.onNodeWithText("protobuf", substring = true, ignoreCase = true, useUnmergedTree = true).assertIsDisplayed()
    }
}
