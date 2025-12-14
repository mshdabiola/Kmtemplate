/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.kmtemplate

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout

import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kmtemplate.ui.Compact
import com.mshdabiola.kmtemplate.ui.KmtAppState
import com.mshdabiola.kmtemplate.ui.KmtScaffold
import com.mshdabiola.kmtemplate.ui.Medium
import com.mshdabiola.kmtemplate.ui.rememberKmtAppState
import com.mshdabiola.model.testtag.KmtScaffoldTestTags
import com.mshdabiola.ui.LocalSharedTransitionScope
import org.junit.Rule
import org.junit.Test

@OptIn( ExperimentalSharedTransitionApi::class)
class KmtScaffoldScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    private fun TestAppScaffold(
        appState: KmtAppState,
    ) {
        SharedTransitionLayout {
            CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                KmtTheme {
                    KmtScaffold(
                        appState = appState,
                    ) {}
                }
            }
        }
    }

    @Test
    fun kmtScaffold_compactState_displaysModalDrawerAndFab() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(300, 800)
            appState = rememberKmtAppState(windowSizeClass = windowSizeClass)

            // Open drawer to make its content available for testing
            LaunchedEffect(Unit) {
                (appState as? Compact)?.drawerState?.open()
            }

            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.MODAL_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.MODAL_DRAWER_SHEET).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            KmtScaffoldTestTags.DrawerContentTestTags.DRAWER_CONTENT_COLUMN,
        ).assertIsDisplayed()

        // Check for FAB in compact mode
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.FAB_ANIMATED_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.EXTENDED_FAB).assertIsDisplayed()
    }

    @Test
    fun kmtScaffold_mediumState_railCollapsed_displaysWideRailAndSmallFab() {
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(700, 800)
            val appState = rememberKmtAppState(windowSizeClass = windowSizeClass)

            // Ensure rail is collapsed
            LaunchedEffect(Unit) {
                (appState as? Medium)?.collapse()
            }

            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.WIDE_NAVIGATION_RAIL).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            KmtScaffoldTestTags.DrawerContentTestTags.DRAWER_CONTENT_COLUMN,
        ).assertIsDisplayed()

        // Check for Small FAB when rail is collapsed
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.FAB_ANIMATED_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.SMALL_FAB).assertIsDisplayed()
    }

    @Test
    fun kmtScaffold_mediumState_railExpanded_displaysWideRailAndExtendedFab() {
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(700, 800)
            val appState = rememberKmtAppState(
                windowSizeClass = windowSizeClass,
                wideNavigationRailState = rememberWideNavigationRailState(WideNavigationRailValue.Expanded),
            )
            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.WIDE_NAVIGATION_RAIL).assertIsDisplayed()

        // Check for Extended FAB when rail is expanded
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.FAB_ANIMATED_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.EXTENDED_FAB).assertIsDisplayed()
    }

    @Test
    fun kmtScaffold_mediumState_railToggleButton_changesFabState() {
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(700, 800)
            val appState = rememberKmtAppState(windowSizeClass = windowSizeClass)
            TestAppScaffold(appState)
        }

        // Initially Small FAB is visible
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.SMALL_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.EXTENDED_FAB).assertDoesNotExist()

        // Click to expand
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.RAIL_TOGGLE_BUTTON).performClick()
        composeTestRule.waitForIdle()

        // Now Extended FAB is visible
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.EXTENDED_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.SMALL_FAB).assertDoesNotExist()
    }

    @Test
    fun kmtScaffold_expandState_displaysPermanentDrawerSheet() {
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(900, 800)
            val appState = rememberKmtAppState(windowSizeClass = windowSizeClass)
            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_DRAWER_SHEET).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            KmtScaffoldTestTags.DrawerContentTestTags.DRAWER_CONTENT_COLUMN,
        ).assertIsDisplayed()
    }

    @Test
    fun kmtScaffold_fabNotDisplayed_when_isNotMainRoute() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(300, 800)
            appState = rememberKmtAppState(windowSizeClass = windowSizeClass)
            TestAppScaffold(appState)
        }

        // Initially, FAB is displayed on the main route
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.FAB_ANIMATED_CONTENT).assertIsDisplayed()

        // Navigate to a different screen
        composeTestRule.runOnUiThread {
            appState.navController.navigateToDetail(Detail(1)) // Use the extension function
        }

        // FAB should no longer be displayed
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.FAB_ANIMATED_CONTENT).assertDoesNotExist()
    }
}
