/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import com.mshdabiola.skeletonapp.ui.SkAppState
import com.mshdabiola.skeletonapp.ui.rememberSkAppState
import com.mshdabiola.testing.util.TestNetworkMonitor
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import kotlin.test.assertEquals

/**
 * Tests [NiaAppState].
 *
 * Note: This could become an unit test if Robolectric is added to the project and the Context
 * is faked.
 */
class AppStateTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val networkMonitor = TestNetworkMonitor()

    // Subject under test.
    private lateinit var state: SkAppState

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun niaAppState_currentDestination() = runTest {
        var currentDestination: String? = null

        composeTestRule.setContent {
            val navController = rememberTestNavController()
            state =
//                remember(navController) {
                SkAppState(
                    navController = navController,
                    coroutineScope = backgroundScope,
                    windowSizeClass = calculateWindowSizeClass(),
                )
//            }

            // Update currentDestination whenever it changes
            currentDestination = state.currentDestination?.route

            // Navigate to destination b once
            LaunchedEffect(Unit) {
                navController.setCurrentDestination("b")
            }
        }

        assertEquals("b", currentDestination)
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun niaAppState_destinations() = runTest {
        composeTestRule.setContent {
            val navController = rememberTestNavController()

            state = rememberSkAppState(
                navController = navController,
                coroutineScope = backgroundScope,
                windowSizeClass = calculateWindowSizeClass(),
            )
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun niaAppState_whenNetworkMonitorIsOffline_StateIsOffline() = runTest(UnconfinedTestDispatcher()) {
        composeTestRule.setContent {
            state = SkAppState(
                navController = NavHostController(LocalContext.current),
                coroutineScope = backgroundScope,
                windowSizeClass = calculateWindowSizeClass(),
            )
        }
    }
}

@Composable
private fun rememberTestNavController(): TestNavHostController {
    val context = LocalContext.current
    return remember {
        TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            graph = createGraph(startDestination = "a") {
                composable("a") { }
                composable("b") { }
                composable("c") { }
            }
        }
    }
}
