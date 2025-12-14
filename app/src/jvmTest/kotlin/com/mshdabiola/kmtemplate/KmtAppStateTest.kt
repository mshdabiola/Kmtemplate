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

import androidx.compose.material3.DrawerValue

import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation3.runtime.NavBackStack
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.kmtemplate.ui.Compact
import com.mshdabiola.kmtemplate.ui.Expand
import com.mshdabiola.kmtemplate.ui.KmtAppState
import com.mshdabiola.kmtemplate.ui.Medium
import com.mshdabiola.kmtemplate.ui.rememberKmtAppState
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.model.Notification
import com.mshdabiola.setting.navigation.Setting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3ExpressiveApi::class)
class KmtAppStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testCoroutineScope: CoroutineScope

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Helper to initialize the [KmtAppState] and a [NavBackStack] within a Composable context.
     */
    private fun initializeAppState(
        width: Int,
        height: Int = 600,
    ): KmtAppState {
        lateinit var appState: KmtAppState
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(width, height)
            testCoroutineScope = rememberCoroutineScope()
            appState = rememberKmtAppState(
                windowSizeClass = windowSizeClass,
                coroutineScope = testCoroutineScope,
            )
        }
        return appState
    }

    @Test
    fun `rememberKmtAppState returns Compact for compact width`() {
        val state = initializeAppState(width = 400)
        Assert.assertTrue("State should be Compact for compact width", state is Compact)
    }

    @Test
    fun `rememberKmtAppState returns Medium for medium width`() {
        val state = initializeAppState(width = 700)
        Assert.assertTrue("State should be Medium for medium width", state is Medium)
    }

    @Test
    fun `rememberKmtAppState returns Expand for expanded width`() {
        val state = initializeAppState(width = 900)
        Assert.assertTrue("State should be Expand for expanded width", state is Expand)
    }

    @Test
    fun `initial state has Main as current destination`() = runTest {
        val state = initializeAppState(width = 400)
        advanceUntilIdle()
        Assert.assertTrue(state.isMain.first())
    }

    @Test
    fun `navigateTopRoute navigates correctly`() = runTest {
        val state = initializeAppState(width = 400)
        advanceUntilIdle()

        // Navigate to Settings
        state.navigateTopRoute(Setting)
        advanceUntilIdle()

        Assert.assertTrue(state.currentRoute.first() == Setting)

        // Navigate back to Main
        state.navigateTopRoute(Main)
        advanceUntilIdle()

        Assert.assertTrue(state.currentRoute.first() == Main)
    }

    @Test
    fun `isInCurrentRoute correctly identifies the current route`() = runTest {
        val state = initializeAppState(width = 400)
        advanceUntilIdle()

        // Initial route is Main
        Assert.assertTrue(state.isInCurrentRoute(Main))
        Assert.assertFalse(state.isInCurrentRoute(Setting))

        // Navigate to Setting
        state.navigateTopRoute(Setting)
        advanceUntilIdle()

        Assert.assertTrue(state.isInCurrentRoute(Setting))
        Assert.assertFalse(state.isInCurrentRoute(Main))
    }

    @Test
    fun `Compact state onDrawerToggle opens and closes drawer`() = runTest {
        val state = initializeAppState(width = 400) as Compact
        Assert.assertEquals(DrawerValue.Closed, state.drawerState.currentValue)

        // Open drawer
        state.onDrawerToggle()
        advanceUntilIdle()
        Assert.assertEquals(DrawerValue.Open, state.drawerState.currentValue)

        // Close drawer
        state.onDrawerToggle()
        advanceUntilIdle()
        Assert.assertEquals(DrawerValue.Closed, state.drawerState.currentValue)
    }

    @Test
    fun `Medium state expand and collapse updates rail state and isExpanded`() = runTest {
        val state = initializeAppState(width = 700) as Medium
        Assert.assertEquals(WideNavigationRailValue.Collapsed, state.wideNavigationRailState.currentValue)
        Assert.assertFalse(state.isExpanded)

        // Expand rail
        state.expand()

        advanceUntilIdle()
        composeTestRule.mainClock.advanceTimeBy(1000)

        Assert.assertEquals(WideNavigationRailValue.Expanded, state.wideNavigationRailState.currentValue)
        Assert.assertTrue(state.isExpanded)

        // Collapse rail
        state.collapse()
        advanceUntilIdle()
        composeTestRule.mainClock.advanceTimeBy(1000)
        Assert.assertEquals(WideNavigationRailValue.Collapsed, state.wideNavigationRailState.currentValue)
        Assert.assertFalse(state.isExpanded)
    }

    @Test
    fun `Expand state isExpanded is always true`() {
        val state = initializeAppState(width = 900) as Expand
        Assert.assertTrue(state.isExpanded)
    }

    @Test
    fun `onNotification shows snackbar`() = runTest {
        val state = initializeAppState(width = 400)
        val testMessage = "Test Snackbar"

        // Pre-condition: no snackbar is visible
        Assert.assertEquals(null, state.snackbarHostState.currentSnackbarData)

        // Trigger notification
        state.onNotification(Notification.Message(message = testMessage))
        advanceUntilIdle() // Allow snackbar coroutine to launch

        // Assert snackbar is shown with the correct message
        Assert.assertNotNull(state.snackbarHostState.currentSnackbarData)
        Assert.assertEquals(testMessage, state.snackbarHostState.currentSnackbarData?.visuals?.message)

        // Dismiss to clean up state for other tests
        state.snackbarHostState.currentSnackbarData?.dismiss()
    }
}
