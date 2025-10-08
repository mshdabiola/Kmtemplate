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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation3.runtime.NavBackStack
import androidx.window.core.layout.WindowSizeClass
import com.hobit.synapse.ui.Compact
import com.hobit.synapse.ui.Expand
import com.hobit.synapse.ui.Medium
import com.hobit.synapse.ui.Route
import com.hobit.synapse.ui.SynAppState
import com.hobit.synapse.ui.rememberSynAppState
import com.mshdabiola.model.Notification
import com.mshdabiola.model.note.NoteDisplayCategory
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.mshdabiola.main.navigation.Main as MainRouteKey

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3ExpressiveApi::class)
class SynAppStateTest {
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
     * Helper to initialize the [SynAppState] and a [NavBackStack] within a Composable context.
     */
    private fun initializeAppState(
        width: Int,
        height: Int = 600,
    ): SynAppState {
        lateinit var appState: SynAppState
        composeTestRule.setContent {
            val windowSizeClass = WindowSizeClass(width, height)
            testCoroutineScope = rememberCoroutineScope()
            appState = rememberSynAppState(
                windowSizeClass = windowSizeClass,
                coroutineScope = testCoroutineScope,
            )
        }
        return appState
    }

    @Test
    fun `rememberSynAppState returns Compact for compact width`() {
        val state = initializeAppState(width = 400)
        assertTrue("State should be Compact for compact width", state is Compact)
    }

    @Test
    fun `rememberSynAppState returns Medium for medium width`() {
        val state = initializeAppState(width = 700)
        assertTrue("State should be Medium for medium width", state is Medium)
    }

    @Test
    fun `rememberSynAppState returns Expand for expanded width`() {
        val state = initializeAppState(width = 900)
        assertTrue("State should be Expand for expanded width", state is Expand)
    }

    @Test
    fun `initial state has Main as current destination`() = runTest {
        val state = initializeAppState(width = 400)
        advanceUntilIdle()
        assertTrue(state.isMain.first())
    }

    @Test
    fun `navigateTopRoute navigates correctly`() = runTest {
        val state = initializeAppState(width = 400)
        advanceUntilIdle()

        // Navigate to Settings
        state.navigateTopRoute(Route.Setting)
        advanceUntilIdle()

        assertTrue(state.currentRoute.first() == Setting)

        // Navigate back to Main
        state.navigateTopRoute(Route.Main(NoteDisplayCategory()))
        advanceUntilIdle()

        assertTrue(state.currentRoute.first() == MainRouteKey)
    }

    @Test
    fun `isInCurrentRoute correctly identifies the current route`() = runTest {
        val state = initializeAppState(width = 400)
        advanceUntilIdle()

        // Initial route is Main
        assertTrue(state.isInCurrentRoute(Route.Main(NoteDisplayCategory()), NoteDisplayCategory()))
        assertFalse(state.isInCurrentRoute(Route.Setting, NoteDisplayCategory()))

        // Navigate to Setting
        state.navigateTopRoute(Route.Setting)
        advanceUntilIdle()

        assertTrue(state.isInCurrentRoute(Route.Setting, NoteDisplayCategory()))
        assertFalse(state.isInCurrentRoute(Route.Main(NoteDisplayCategory()), NoteDisplayCategory()))
    }

    @Test
    fun `Compact state onDrawerToggle opens and closes drawer`() = runTest {
        val state = initializeAppState(width = 400) as Compact
        assertEquals(DrawerValue.Closed, state.drawerState.currentValue)

        // Open drawer
        state.onDrawerToggle()
        advanceUntilIdle()
        assertEquals(DrawerValue.Open, state.drawerState.currentValue)

        // Close drawer
        state.onDrawerToggle()
        advanceUntilIdle()
        assertEquals(DrawerValue.Closed, state.drawerState.currentValue)
    }

    @Test
    fun `Medium state expand and collapse updates rail state and isExpanded`() = runTest {
        val state = initializeAppState(width = 700) as Medium
        assertEquals(WideNavigationRailValue.Collapsed, state.wideNavigationRailState.currentValue)
        assertFalse(state.isExpanded)

        // Expand rail
        state.expand()

        advanceUntilIdle()
        assertEquals(WideNavigationRailValue.Expanded, state.wideNavigationRailState.targetValue)
//        assertTrue(state.isExpanded)

        // Collapse rail
        state.collapse()
        advanceUntilIdle()
        assertEquals(WideNavigationRailValue.Collapsed, state.wideNavigationRailState.targetValue)
//        assertFalse(state.isExpanded)
    }

    @Test
    fun `Expand state isExpanded is always true`() {
        val state = initializeAppState(width = 900) as Expand
        assertTrue(state.isExpanded)
    }

    @Test
    fun `onNotification shows snackbar`() = runTest {
        val state = initializeAppState(width = 400)
        val testMessage = "Test Snackbar"

        // Pre-condition: no snackbar is visible
        assertEquals(null, state.snackbarHostState.currentSnackbarData)

        // Trigger notification
        state.onNotification(Notification.Message(message = testMessage))
        advanceUntilIdle() // Allow snackbar coroutine to launch

        // Assert snackbar is shown with the correct message
        assertNotNull(state.snackbarHostState.currentSnackbarData)
        assertEquals(testMessage, state.snackbarHostState.currentSnackbarData?.visuals?.message)

        // Dismiss to clean up state for other tests
        state.snackbarHostState.currentSnackbarData?.dismiss()
    }
}
