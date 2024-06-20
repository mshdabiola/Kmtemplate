/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.mshdabiola.testing.fake.testDataModule
import com.mshdabiola.testing.rules.GrantPostNotificationsPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import kotlin.properties.ReadOnlyProperty

/**
 * Tests all the navigation flows that are handled by the navigation library.
 */

class FullTest : KoinTest {

    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule.create {
        val module = module {
        }
        // Your KoinApplication instance here
        modules(module, testDataModule)
    }

    /**
     * Create a temporary folder used to create a Data Store file. This guarantees that
     * the file is removed in between each test, preventing a crash.
     */
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    /**
     * Grant [android.Manifest.permission.POST_NOTIFICATIONS] permission.
     */
    @get:Rule(order = 2)
    val postNotificationsPermission = GrantPostNotificationsPermissionRule()

    /**
     * Use the primary activity to initialize the app normally.
     */
    @get:Rule(order = 3)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes resId: Int) =
        ReadOnlyProperty<Any, String> { _, _ -> activity.getString(resId) }

    @Test
    fun full() {
        composeTestRule.apply {
            // VERIFY for you is selected
            repeat(10) {
                onNodeWithText("Add Note").performClick()

                this.waitForIdle()

                onNodeWithTag("detail:title").performTextInput("title")

                onNodeWithTag("detail:content").performTextInput("content")

                onNodeWithContentDescription("back").performClick()
            }

            onNodeWithTag("main:list").performScrollTo()
        }
    }
}
