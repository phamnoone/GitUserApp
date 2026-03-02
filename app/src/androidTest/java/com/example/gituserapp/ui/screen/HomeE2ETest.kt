package com.example.gituserapp.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.performScrollToIndex
import com.example.gituserapp.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun searchGoogle_displaysItems_and_scrollTriggersLoadMore() {
        composeTestRule.onNodeWithText("ユーザーを検索…").performTextInput("google")
        
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("google", substring = true, ignoreCase = true).assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }

        composeTestRule.onNodeWithText("google", substring = true, ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("google-research", substring = true, ignoreCase = true).assertIsDisplayed()

        composeTestRule.onRoot().printToLog("HOME_SCREEN")
        
        composeTestRule.onNode(hasScrollToIndexAction()).performScrollToIndex(1)
        
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("google-api", substring = true, ignoreCase = true).assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }
        composeTestRule.onNodeWithText("google-api", substring = true, ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun searchGzza1_displaysNoMatchingRepositoriesText() {
        composeTestRule.onNodeWithText("ユーザーを検索…").performTextInput("gzza1")

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("一致するユーザーがありません").assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }
        composeTestRule.onNodeWithText("一致するユーザーがありません").assertIsDisplayed()
    }
}
