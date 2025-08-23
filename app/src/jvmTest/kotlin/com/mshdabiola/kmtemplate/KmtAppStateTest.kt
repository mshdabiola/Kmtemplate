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

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.WideNavigationRailState
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.kmtemplate.ui.Compact
import com.mshdabiola.kmtemplate.ui.KmtAppState
import com.mshdabiola.kmtemplate.ui.Medium
import com.mshdabiola.kmtemplate.ui.rememberKmtAppState
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3ExpressiveApi::class)
class KmtAppStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavHostController
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testCoroutineScope: CoroutineScope
    private lateinit var wideNavigationRailState: WideNavigationRailState

    @Before
    fun setUp() {
//        Dispatchers.setMain(testDispatcher)
        composeTestRule.setContent {
            navController = rememberNavController().apply {
                graph =
                    createGraph(startDestination = Main) {
                        composable<Main> { }
                        composable<Detail> { }
                        composable<Setting> { }
                    }
            }
            testCoroutineScope = rememberCoroutineScope()
            wideNavigationRailState = rememberWideNavigationRailState() // Initial state is Collapsed
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initializeStateAndNavHostForNavigationTests(width: Int): KmtAppState {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            // For navigation tests, we can use any state, e.g., Compact
            val compactWindowSize = WindowSizeClass(width, 600)
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            appState = rememberKmtAppState(
                windowSizeClass = compactWindowSize,
                coroutineScope = testCoroutineScope,
                navController = navController,
                drawerState = drawerState,
                wideNavigationRailState = wideNavigationRailState,
            )
        }
        return appState
    }

    @Test
    fun navigateTopRoute_navigatesToMainCorrectly() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300)
        advanceUntilIdle() // Allow navigation to complete

        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute(Main::class) ?: false)
        assertTrue(state.isInCurrentRoute(Main))
    }

    @Test
    fun navigateTopRoute_navigatesToSettingCorrectly() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300)

        composeTestRule.runOnUiThread { state.navigateTopRoute(Setting) }
        advanceUntilIdle()

        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute(Setting::class) ?: false)
        assertTrue(state.isInCurrentRoute(Setting))
    }

    @Test
    fun isInCurrentRoute_returnsTrueForCurrentRoute() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300)
        composeTestRule.runOnUiThread { state.navigateTopRoute(Main) }
        advanceUntilIdle()
        assertTrue(state.isInCurrentRoute(Main))
    }

    @Test
    fun isInCurrentRoute_returnsFalseForOtherRoute() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300)
        composeTestRule.runOnUiThread { state.navigateTopRoute(Main) }
        advanceUntilIdle()
        assertFalse(state.isInCurrentRoute(Setting))
    }

    private fun getCompactStateWithDrawer(initialDrawerValue: DrawerValue = DrawerValue.Closed): Compact {
        // Need to ensure drawerState is created within a composition for it to be managed correctly if not passed.
        // However, for testing specific logic with a given state, direct instantiation is fine.
        lateinit var drawerState: DrawerState
        composeTestRule.setContent {
            // Recompose to get a fresh drawer state if needed, or use a passed one
            drawerState = rememberDrawerState(initialValue = initialDrawerValue)
        }

        return Compact(
            navController = navController,
            coroutineScope = testCoroutineScope,
            drawerState = drawerState,
            snackbarHostState = SnackbarHostState(),
        )
    }

    @Test
    fun compactState_onDrawer_opensDrawerWhenClosed() = runTest(testDispatcher) {
        val state = getCompactStateWithDrawer(DrawerValue.Closed)
        assertEquals(DrawerValue.Closed, state.drawerState.currentValue)

        state.onDrawerToggle()
        composeTestRule.awaitIdle()

        assertTrue(state.drawerState.isOpen)
    }

    @Test
    fun compactState_onDrawer_closesDrawerWhenOpen() = runTest(testDispatcher) {
        val state = getCompactStateWithDrawer(DrawerValue.Open)
        assertEquals(DrawerValue.Open, state.drawerState.currentValue)

        state.onDrawerToggle()
        composeTestRule.awaitIdle()

        assertTrue(state.drawerState.isClosed)
    }

    @Test
    fun compactState_navigateTopRoute_alsoInvokesOnDrawerAndTogglesIt() = runTest(testDispatcher) {
        // Test with drawer initially open
        val drawerOpenState = getCompactStateWithDrawer(DrawerValue.Open)
        assertEquals(DrawerValue.Open, drawerOpenState.drawerState.currentValue)

        composeTestRule.runOnUiThread {
            drawerOpenState.navigateTopRoute(Main)
        }
        drawerOpenState.onDrawerToggle()
        composeTestRule.awaitIdle()

        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute(Main::class) ?: false)

        assertTrue(
            "Drawer should be closed after navigateTopRoute invoked onDrawer (was open)",
            drawerOpenState.drawerState.isClosed,
        )

        // Test with drawer initially closed
        val drawerClosedState = getCompactStateWithDrawer(DrawerValue.Closed)
        assertEquals(DrawerValue.Closed, drawerClosedState.drawerState.currentValue)

        composeTestRule.runOnUiThread { drawerClosedState.navigateTopRoute(Setting) }
        drawerClosedState.onDrawerToggle()
        composeTestRule.awaitIdle()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute(Setting::class) ?: false)
        assertTrue(
            "Drawer should be open after navigateTopRoute invoked onDrawer (was closed)",
            drawerClosedState.drawerState.isOpen,
        )
    }

    private fun getMediumStateWithRail(): Medium {
        // wideNavigationRailState is initialized in setUp and reset for each test via setContent
        return Medium(
            navController = navController,
            coroutineScope = testCoroutineScope,
            wideNavigationRailState = wideNavigationRailState,
            snackbarHostState = SnackbarHostState(),
        )
    }

    @Test
    fun mediumState_expand_expandsWideNavigationRail() = runTest(testDispatcher) {
        val state = getMediumStateWithRail()
        // wideNavigationRailState is initialValue = WideNavigationRailValue.Collapsed by default from rememberWideNavigationRailState()
        assertTrue("Initially, rail should be collapsed", wideNavigationRailState.isCollapsed)
        assertFalse("Initially, rail should not be expanded", wideNavigationRailState.isExpanded)

        state.expand()
        composeTestRule.awaitIdle()

        assertTrue("Rail should be expanded after expand()", wideNavigationRailState.isExpanded)
        assertFalse("Rail should not be collapsed after expand()", wideNavigationRailState.isCollapsed)
    }

    @Test
    fun mediumState_collapse_collapsesWideNavigationRail() = runTest(testDispatcher) {
        val state = getMediumStateWithRail()

        // First, expand it to ensure collapse has an effect
        state.expand()
        composeTestRule.awaitIdle()
        assertTrue("Rail should be expanded before collapsing", wideNavigationRailState.isExpanded)
        assertFalse(
            "Rail should not be collapsed before attempting to collapse an expanded rail",
            wideNavigationRailState.isCollapsed,
        )

        state.collapse()
        composeTestRule.awaitIdle()

        assertFalse("Rail should not be expanded after collapse()", wideNavigationRailState.isExpanded)
        assertTrue("Rail should be collapsed after collapse()", wideNavigationRailState.isCollapsed)
    }

    val WideNavigationRailState.isExpanded
        get() = this.currentValue == WideNavigationRailValue.Expanded

    val WideNavigationRailState.isCollapsed
        get() = currentValue == WideNavigationRailValue.Collapsed
}
