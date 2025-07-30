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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import co.touchlab.kermit.koin.kermitLoggerModule
import com.mshdabiola.detail.DetailScreenTestTags
import com.mshdabiola.detail.detailModule
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtAppState
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtAppTestTags
import com.mshdabiola.kotlinmultiplatformtemplate.ui.SplashScreen
import com.mshdabiola.kotlinmultiplatformtemplate.ui.SplashScreenTestTags
import com.mshdabiola.kotlinmultiplatformtemplate.ui.rememberKmtAppState
import com.mshdabiola.kotlinmultiplatformtemplate.util.KoinTestRule
import com.mshdabiola.kotlinmultiplatformtemplate.util.TestLifecycleOwner
import com.mshdabiola.main.MainScreenTestTags
import com.mshdabiola.main.mainModule
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.model.getLoggerWithTag
import com.mshdabiola.setting.SettingScreenTestTags
import com.mshdabiola.setting.navigation.Setting
import com.mshdabiola.setting.settingModule
import com.mshdabiola.testing.fake.testDataModule
import com.mshdabiola.testing.util.testLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest

class KmtAppTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var testLifecycleOwner: TestLifecycleOwner
    private lateinit var appState: KmtAppState
    val appModule =
        module {
            includes(testDataModule, detailModule, mainModule, settingModule)
            viewModel {
                MainAppViewModel(
                    get(),
                    getLoggerWithTag("MainAppViewModel"),
                )
            }
        }

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(
            appModule,
            kermitLoggerModule(testLogger),
        ),
    )

    @Before
    fun init() {
        testLifecycleOwner = TestLifecycleOwner(composeTestRule)
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        // You might want to move it to RESUMED before setContent if your UI expects it
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @After
    fun tearDown() {
        println("tearDown")
        // Allow Compose and Navigation to settle before tearing down lifecycle
        composeTestRule.waitForIdle()

        // Bring lifecycle down gracefully
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        composeTestRule.waitForIdle() // Allow observers to react
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        composeTestRule.waitForIdle() // Allow observers to react

        // At this point, NavBackStackEntry lifecycles should have also transitioned down if they were active.
        // Destroy the main lifecycle owner
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        composeTestRule.waitForIdle() // Final settle
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun KmtApp(windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.COMPACT) {
        val testCoroutineScope = CoroutineScope(StandardTestDispatcher())

        val windowSizeClass = WindowSizeClass.compute(
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.COMPACT -> 300f // dpWidth
                WindowWidthSizeClass.MEDIUM -> 700f
                WindowWidthSizeClass.EXPANDED -> 900f
                else -> 300f
            },
            800f, // dpHeight, less critical for these tests
        )
        appState = rememberKmtAppState(windowSizeClass, testCoroutineScope)

        CompositionLocalProvider(
            LocalViewModelStoreOwner provides object : ViewModelStoreOwner {
                override val viewModelStore = ViewModelStore()
            },
            LocalLifecycleOwner provides testLifecycleOwner,
        ) {
            val show = remember { mutableStateOf(true) }
            LaunchedEffect(Unit) {
                delay(2000)
                show.value = false
            }
            Box(Modifier.fillMaxSize()) {
                com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtApp(appState = appState)
                if (show.value) {
                    SplashScreen()
                }
            }
        }
    }

    @Test
    fun kmtApp_initialStructure_isDisplayed_compact() {
        composeTestRule.setContent {
            KmtApp()
        }

        // Check for the root layout
        composeTestRule.onNodeWithTag(KmtAppTestTags.APP_ROOT_LAYOUT)
            .assertExists("App root layout should exist")
            .assertIsDisplayed()

        // Check for the gradient background
        composeTestRule.onNodeWithTag(KmtAppTestTags.GRADIENT_BACKGROUND)
            .assertExists("Gradient background should exist")
            .assertIsDisplayed()

        // Check for the main scaffold
        composeTestRule.onNodeWithTag(KmtAppTestTags.MAIN_SCAFFOLD)
            .assertExists("Main scaffold should exist")
            .assertIsDisplayed()

        // Check for the NavHost
        composeTestRule.onNodeWithTag(KmtAppTestTags.NAV_HOST)
            .assertExists("NavHost should exist")
            .assertIsDisplayed()
    }

    @Test
    fun kmtApp_initialStructure_isDisplayed_compact2() {
        composeTestRule.setContent {
            KmtApp()
        }

        // Wait for splash screen to disappear if it blocks initial UI
//        composeTestRule.waitUntil(timeoutMillis = 3000) {
        composeTestRule.onNodeWithTag(SplashScreenTestTags.SCREEN_ROOT) // Assuming SplashScreen has this tag
            .isNotDisplayed()
//        }

        // Check for the root layout
        composeTestRule.onNodeWithTag(KmtAppTestTags.APP_ROOT_LAYOUT)
            .assertExists("App root layout should exist")
            .assertIsDisplayed()

        // Check for the gradient background
        composeTestRule.onNodeWithTag(KmtAppTestTags.GRADIENT_BACKGROUND)
            .assertExists("Gradient background should exist")
            .assertIsDisplayed()

        // Check for the main scaffold
        composeTestRule.onNodeWithTag(KmtAppTestTags.MAIN_SCAFFOLD)
            .assertExists("Main scaffold should exist")
            .assertIsDisplayed()

        // Check for the NavHost
        composeTestRule.onNodeWithTag(KmtAppTestTags.NAV_HOST)
            .assertExists("NavHost should exist")
            .assertIsDisplayed()
    }

    @Test
    fun kmtApp_verifyInitialScreen_isMainScreen() {
        composeTestRule.setContent {
            KmtApp()
        }
        // Wait for splash screen to potentially disappear
        composeTestRule.onAllNodesWithTag(SplashScreenTestTags.SCREEN_ROOT).fetchSemanticsNodes().isEmpty()

        // This tag should be on a unique element within your main screen.
        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT)
            .assertIsDisplayed()
    }

    @Test
    fun kmtApp_navigateToSettingsScreen_andVerify() {
        composeTestRule.setContent {
            KmtApp(windowWidthSizeClass = WindowWidthSizeClass.EXPANDED)
        }
        composeTestRule.onAllNodesWithTag(SplashScreenTestTags.SCREEN_ROOT).fetchSemanticsNodes().isEmpty()

        composeTestRule.runOnUiThread {
            appState.navigateTopRoute(Setting)
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SettingScreenTestTags.SCREEN_ROOT, useUnmergedTree = true)
            .assertIsDisplayed()

        // Verify that the main screen is no longer visible (or not the primary one)
        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT)
            .assertDoesNotExist() // Or .assertIsNotDisplayed() if it's still in composition but hidden
    }

    @Test
    fun kmtApp_navigateToDetailScreen_fromMainScreen_andNavigateBack() {
        composeTestRule.setContent {
            KmtApp()
        }
        composeTestRule.onAllNodesWithTag(SplashScreenTestTags.SCREEN_ROOT).fetchSemanticsNodes().isEmpty()

        // 1. Ensure we are on the Main Screen
        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        // This could be clicking a list item. You'll need a test tag for that item.
        // For example:
        composeTestRule.runOnUiThread {
            appState.navController.navigateToDetail(Detail(-1))
        }

        composeTestRule.onNodeWithTag(DetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        composeTestRule.onNodeWithTag(DetailScreenTestTags.TITLE_TEXT_FIELD)
            .performTextInput("Title")

        composeTestRule.onNodeWithTag(DetailScreenTestTags.CONTENT_TEXT_FIELD)
            .performTextInput("content")

        // Navigate to Settings

        composeTestRule.runOnUiThread {
            appState.navigateTopRoute(Setting)
        }
        composeTestRule.onNodeWithTag(SettingScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        // If you have a bottom nav item for "Main":

        composeTestRule.runOnUiThread {
            appState.navigateTopRoute(Main)
        }

        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SettingScreenTestTags.SCREEN_ROOT).assertDoesNotExist() // Or IsNotDisplayed
    }
}
