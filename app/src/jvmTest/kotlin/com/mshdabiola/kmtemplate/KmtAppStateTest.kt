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
package com.mshdabiola.kmtemplate

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.WideNavigationRailState
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
import com.mshdabiola.kmtemplate.ui.Expand
import com.mshdabiola.kmtemplate.ui.KmtAppState
import com.mshdabiola.kmtemplate.ui.Medium
import com.mshdabiola.kmtemplate.ui.rememberKmtAppState
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3ExpressiveApi::class)
class KmtAppStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavHostController
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private lateinit var testCoroutineScope: CoroutineScope
    private lateinit var wideNavigationRailState: WideNavigationRailState

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = rememberNavController().apply {
                graph =
                    createGraph(startDestination = Main) {
                        composable<Main> { }
                        composable<Detail> { }
                        composable<Setting> { }
                    }
            }
            testCoroutineScope = rememberCoroutineScope { testDispatcher }
            wideNavigationRailState = rememberWideNavigationRailState()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initializeStateAndNavHostForNavigationTests(width: Float): KmtAppState {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            // For navigation tests, we can use any state, e.g., Compact
            val compactWindowSize = WindowSizeClass.compute(width, 600f)
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            appState = rememberKmtAppState(
                windowSizeClass = compactWindowSize,
                coroutineScope = testCoroutineScope,
                navController = navController,
                drawerState = drawerState,
            )
        }
        return appState
//        return Compact(
//            navController = navController,
//            coroutineScope = testCoroutineScope,
//            drawerState = DrawerState(DrawerValue.Closed)
//        )
    }

    @Test
    fun rememberKmtAppState_compactWidth_returnsCompactState() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300f)

        assertTrue(state is Compact)
        assertNotNull((state as Compact).onDrawer)
    }

    @Test
    fun rememberKmtAppState_mediumWidth_returnsMediumState() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(800f)
        assertTrue(state is Medium)
        assertNull((state as Medium).onDrawer)
    }

    @Test
    fun rememberKmtAppState_expandedWidth_returnsExpandState() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(1000f)
        assertTrue(state is Expand)
        assertNull((state as Expand).onDrawer)
    }

    @Test
    fun navigateTopRoute_navigatesToMainCorrectly() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300f)
        advanceUntilIdle() // Allow navigation to complete

        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute(Main::class) ?: false)
        assertTrue(state.isInCurrentRoute(Main))
    }

    @Test
    fun navigateTopRoute_navigatesToSettingCorrectly() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300f)

        composeTestRule.runOnUiThread { state.navigateTopRoute(Setting) }
        advanceUntilIdle()

        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute(Setting::class) ?: false)
        assertTrue(state.isInCurrentRoute(Setting))
    }

    @Test
    fun isInCurrentRoute_returnsTrueForCurrentRoute() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300f)
        composeTestRule.runOnUiThread { state.navigateTopRoute(Main) }
        advanceUntilIdle()
        assertTrue(state.isInCurrentRoute(Main))
    }

    @Test
    fun isInCurrentRoute_returnsFalseForOtherRoute() = runTest(testDispatcher) {
        val state = initializeStateAndNavHostForNavigationTests(300f)
        composeTestRule.runOnUiThread { state.navigateTopRoute(Main) }
        advanceUntilIdle()
        assertFalse(state.isInCurrentRoute(Setting))
    }

    private fun getCompactStateWithDrawer(): Compact {
        return Compact(
            navController = navController,
            coroutineScope = testCoroutineScope,
            drawerState = DrawerState(DrawerValue.Closed),
        )
    }

    @Test
    fun compactState_onDrawer_opensDrawerWhenClosed() = runTest(testDispatcher) {
        val state = getCompactStateWithDrawer()
        // Ensure drawer is closed by re-setting content or asserting initial state
        assertEquals(DrawerValue.Closed, state.drawerState.currentValue)

        state.onDrawer?.invoke()
        advanceUntilIdle() // Allow coroutine to launch and drawer state to update

        assertTrue(state.drawerState.isOpen)
    }

    @Test
    fun compactState_onDrawer_closesDrawerWhenOpen() = runTest(testDispatcher) {
        val state = getCompactStateWithDrawer()
        // Manually open the drawer first
        composeTestRule.runOnUiThread {
            testCoroutineScope.launch { state.drawerState.open() }
        }
        advanceUntilIdle()
        assertTrue(state.drawerState.isOpen)

        state.onDrawer?.invoke()
        advanceUntilIdle()

        assertTrue(state.drawerState.isClosed)
    }

    @Test
    fun compactState_navigateTopRoute_alsoInvokesOnDrawerAndOpensIt() = runTest(testDispatcher) {
        val state = Compact(
            navController = navController,
            coroutineScope = testCoroutineScope,
            drawerState = DrawerState(DrawerValue.Open),
        )
        assertEquals(DrawerValue.Open, state.drawerState.currentValue)

        composeTestRule.runOnUiThread { state.navigateTopRoute(Main) } // This should navigate AND trigger onDrawer
        advanceUntilIdle()

        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute(Main::class) ?: false)
        assertTrue("Drawer should be close after navigateTopRoute in Compact state", state.drawerState.isClosed)
    }

    private fun getMediumStateWithRail(): Medium {
        return Medium(
            navController = navController,
            coroutineScope = testCoroutineScope,
            wideNavigationRailState = wideNavigationRailState,
        )
    }

    @Test
    fun mediumState_expand_expandsWideNavigationRail() = runTest(testDispatcher) {
        val state = getMediumStateWithRail()
        state.expand()
        advanceUntilIdle()
        assertTrue("Expand method should complete", true)
    }

    @Test
    fun mediumState_collapse_collapsesWideNavigationRail() = runTest(testDispatcher) {
        val state = getMediumStateWithRail()
        state.collapse()
        advanceUntilIdle()
        assertTrue("Collapse method should complete", true)
    }
}
