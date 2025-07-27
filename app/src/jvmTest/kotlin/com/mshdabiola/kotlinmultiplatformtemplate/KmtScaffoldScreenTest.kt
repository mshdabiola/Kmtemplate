/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.kotlinmultiplatformtemplate

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kotlinmultiplatformtemplate.ui.DrawerContentTestTags
import com.mshdabiola.kotlinmultiplatformtemplate.ui.FabTestTags
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtAppState
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtScaffold
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtScaffoldTestTags
import com.mshdabiola.kotlinmultiplatformtemplate.ui.TOP_LEVEL_ROUTES
import com.mshdabiola.kotlinmultiplatformtemplate.ui.rememberKmtAppState
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting
import com.mshdabiola.ui.LocalSharedTransitionScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalSharedTransitionApi::class)
class KmtScaffoldScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockNavController: NavHostController
    private val testCoroutineScope = CoroutineScope(Dispatchers.Unconfined) // Or TestCoroutineScope

    // Helper to create KmtAppState for different scenarios
    @Composable
    private fun createTestAppState(
        windowWidthSizeClass: WindowWidthSizeClass,
        drawerInitialValue: DrawerValue = DrawerValue.Closed, // For ModalNavigationDrawer
        railInitialValue: WideNavigationRailValue = WideNavigationRailValue.Collapsed, // For WideNavigationRail
    ): KmtAppState {
        mockNavController = rememberNavController().apply {
            graph =
                createGraph(startDestination = Main) {
                    composable<Main> { }
                    composable<Detail> { }
                    composable<Setting> { }
                }
        }
        val windowSizeClass = WindowSizeClass.compute(
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.COMPACT -> 300f // dpWidth
                WindowWidthSizeClass.MEDIUM -> 700f
                WindowWidthSizeClass.EXPANDED -> 900f
                else -> 300f
            },
            800f, // dpHeight, less critical for these tests
        )

        return rememberKmtAppState(
            windowSizeClass = windowSizeClass,
            navController = mockNavController,
            drawerState = rememberDrawerState(initialValue = drawerInitialValue),
            wideNavigationRailState = rememberWideNavigationRailState(initialValue = railInitialValue),
            coroutineScope = testCoroutineScope,
        )
    }

    @Composable
    private fun TestAppScaffold(appState: KmtAppState, content: @Composable () -> Unit = { Text("Screen Content") }) {
        SharedTransitionLayout {
            CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                KmtTheme {
                    KmtScaffold(appState = appState) {
                        content()
                    }
                }
            }
        }
    }

    @Test
    fun kmtScaffold_compactState_displaysModalDrawerAndFab() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            appState = createTestAppState(
                windowWidthSizeClass = WindowWidthSizeClass.COMPACT,
                drawerInitialValue = DrawerValue.Open, // Ensure drawer content is composed
            )
            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.MODAL_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.MODAL_DRAWER_SHEET).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DrawerContentTestTags.DRAWER_CONTENT_COLUMN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DrawerContentTestTags.BRAND_ROW).assertIsDisplayed()
        TOP_LEVEL_ROUTES.forEach { route ->
            composeTestRule.onNodeWithTag(DrawerContentTestTags.navigationItemTag(route.route)).assertExists()
        }

        // Check for FAB (assuming KmtAppState isMain = true)
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ANIMATED_CONTENT).assertIsDisplayed()
        // Check for either small or extended FAB based on how Fab composable behaves by default in compact
        // For instance, if it defaults to extended:
        composeTestRule.onNodeWithTag(FabTestTags.EXTENDED_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ADD_ICON).assertIsDisplayed()
    }

    @Test
    fun kmtScaffold_mediumState_railCollapsed_displaysWideRailAndFab() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            appState = createTestAppState(
                windowWidthSizeClass = WindowWidthSizeClass.MEDIUM,
                railInitialValue = WideNavigationRailValue.Collapsed,
            )
            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.WIDE_NAVIGATION_RAIL).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.RAIL_TOGGLE_BUTTON).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DrawerContentTestTags.DRAWER_CONTENT_COLUMN).assertIsDisplayed()
        TOP_LEVEL_ROUTES.forEach { route ->
            composeTestRule.onNodeWithTag(DrawerContentTestTags.wideNavigationRailItemTag(route.route)).assertExists()
        }
        // Check for FAB (Small FAB when rail is collapsed in Medium)
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ANIMATED_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.SMALL_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ADD_ICON).assertIsDisplayed()
    }

    @Test
    fun kmtScaffold_mediumState_railExpanded_displaysWideRailAndFab() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            appState = createTestAppState(
                windowWidthSizeClass = WindowWidthSizeClass.MEDIUM,
                railInitialValue = WideNavigationRailValue.Expanded, // Start expanded
            )
            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.WIDE_NAVIGATION_RAIL).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.RAIL_TOGGLE_BUTTON).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DrawerContentTestTags.DRAWER_CONTENT_COLUMN).assertIsDisplayed()
        // Check for FAB (Extended FAB when rail is expanded in Medium)
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ANIMATED_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.EXTENDED_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ADD_ICON).assertIsDisplayed()
    }

    @Test
    fun kmtScaffold_mediumState_railToggleButton_changesState() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            // Start with rail collapsed
            appState = createTestAppState(
                windowWidthSizeClass = WindowWidthSizeClass.MEDIUM,
                railInitialValue = WideNavigationRailValue.Collapsed,
            )
            TestAppScaffold(appState)
        }

        // Initially Small FAB should be visible
        composeTestRule.onNodeWithTag(FabTestTags.SMALL_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.EXTENDED_FAB).assertDoesNotExist()

        // Click to expand
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.RAIL_TOGGLE_BUTTON).performClick()
        composeTestRule.waitForIdle() // Allow recomposition

        // Now Extended FAB should be visible
        composeTestRule.onNodeWithTag(FabTestTags.EXTENDED_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.SMALL_FAB).assertDoesNotExist()

        // Click to collapse
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.RAIL_TOGGLE_BUTTON).performClick()
        composeTestRule.waitForIdle()

        // Back to Small FAB
        composeTestRule.onNodeWithTag(FabTestTags.SMALL_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FabTestTags.EXTENDED_FAB).assertDoesNotExist()
    }

    @Test
    fun kmtScaffold_expandState_displaysPermanentDrawerSheet() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            appState = createTestAppState(
                windowWidthSizeClass = WindowWidthSizeClass.EXPANDED,
            )
            mockNavController.navigateToDetail(Detail(1))
            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.PERMANENT_DRAWER_SHEET).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DrawerContentTestTags.DRAWER_CONTENT_COLUMN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DrawerContentTestTags.BRAND_ROW).assertIsDisplayed()
        TOP_LEVEL_ROUTES.forEach { route ->
            composeTestRule.onNodeWithTag(DrawerContentTestTags.navigationItemTag(route.route)).assertExists()
        }
        // FAB is not expected in the drawer part of the Expand state by default in your current scaffold logic
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ANIMATED_CONTENT).assertDoesNotExist()
    }

    @Test
    fun kmtScaffold_fabNotDisplayed_when_isMainIsFalse() {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            appState = createTestAppState(
                windowWidthSizeClass = WindowWidthSizeClass.COMPACT,
            )
            mockNavController.navigateToDetail(Detail(1))

            TestAppScaffold(appState)
        }

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.MODAL_NAVIGATION_DRAWER).assertIsDisplayed()
        // FAB should not be displayed
        composeTestRule.onNodeWithTag(FabTestTags.FAB_ANIMATED_CONTENT).assertDoesNotExist()
    }
}
