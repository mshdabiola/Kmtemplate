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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.window.core.layout.WindowSizeClass
import co.touchlab.kermit.koin.kermitLoggerModule
import com.mshdabiola.detail.detailModule
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.kmtemplate.ui.KmtAppState
import com.mshdabiola.kmtemplate.ui.KmtAppTestTags
import com.mshdabiola.kmtemplate.ui.pop
import com.mshdabiola.kmtemplate.ui.rememberKmtAppState
import com.mshdabiola.kmtemplate.util.KoinTestRule
import com.mshdabiola.kmtemplate.util.TestLifecycleOwner
import com.mshdabiola.main.mainModule
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.model.Platform
import com.mshdabiola.model.testtag.KmtScaffoldTestTags
import com.mshdabiola.model.testtag.MainScreenTestTags
import com.mshdabiola.model.testtag.SettingScreenTestTags
import com.mshdabiola.setting.navigation.Setting
import com.mshdabiola.setting.settingModule
import com.mshdabiola.testing.fake.testDataModule
import com.mshdabiola.testing.util.testLogger
import com.mshdabiola.ui.getLoggerWithTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest

class KmtAppTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var testLifecycleOwner: TestLifecycleOwner
    private lateinit var appState: KmtAppState

    val applicationModule = module {
        single { getPlatform() } bind Platform::class
    }
    val appModule =
        module {
            includes(applicationModule, testDataModule, detailModule, mainModule, settingModule)
            viewModel {
                MainAppViewModel(
                    userDataRepository = get(),
                    networkRepository = get(),
                    logger = getLoggerWithTag("MainAppViewModel"),
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
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @After
    fun tearDown() {
        composeTestRule.waitForIdle()

        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        composeTestRule.waitForIdle()
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        composeTestRule.waitForIdle()
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        composeTestRule.waitForIdle()
    }


    @Composable
    fun KmtApp(widthSizeClass: Int = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) {
        val testCoroutineScope = CoroutineScope(StandardTestDispatcher())

        val windowSizeClass = WindowSizeClass(widthSizeClass, 800)
        appState = rememberKmtAppState(windowSizeClass, testCoroutineScope)

        CompositionLocalProvider(
            LocalViewModelStoreOwner provides object : ViewModelStoreOwner {
                override val viewModelStore = ViewModelStore()
            },
            LocalLifecycleOwner provides testLifecycleOwner,
        ) {
            Box(Modifier.fillMaxSize()) {
                com.mshdabiola.kmtemplate.ui.KmtApp(appState = appState)
            }
        }
    }

    @Test
    fun kmtApp_initialStructure_isDisplayed() {
        composeTestRule.setContent {
            KmtApp()
        }

        composeTestRule.onNodeWithTag(KmtAppTestTags.APP_ROOT_LAYOUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtAppTestTags.GRADIENT_BACKGROUND).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtAppTestTags.MAIN_SCAFFOLD).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtAppTestTags.NAV_HOST).assertIsDisplayed()
    }

    @Test
    fun kmtApp_initialScreen_isMainScreen() {
        composeTestRule.setContent {
            KmtApp()
        }
        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
    }

    @Test
    fun kmtApp_navigateToSettings_andVerify() {
        composeTestRule.setContent {
            KmtApp(widthSizeClass = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
        }

        composeTestRule.onNodeWithTag(
            KmtScaffoldTestTags.DrawerContentTestTags.navigationItemTag(Setting),
        ).performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SettingScreenTestTags.SCREEN_ROOT, useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
    }

    @Test
    fun kmtApp_navigateToDetail_fromMain_andNavigateBack() {
        composeTestRule.setContent {
            KmtApp()
        }

        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        composeTestRule.runOnUiThread {
            appState.navController.add(Detail(-1))
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.FabTestTags.FAB_ANIMATED_CONTENT).assertDoesNotExist()

        appState.navController.pop()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
    }

    @Test
    fun kmtApp_navigateToSettings_thenNavigateToMain_andVerify() {
        composeTestRule.setContent {
            KmtApp(widthSizeClass = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
        }

        composeTestRule.onNodeWithTag(
            KmtScaffoldTestTags.DrawerContentTestTags.navigationItemTag(Setting),
        ).performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SettingScreenTestTags.SCREEN_ROOT, useUnmergedTree = true).assertIsDisplayed()

        composeTestRule.onNodeWithTag(KmtScaffoldTestTags.DrawerContentTestTags.navigationItemTag(Main)).performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MainScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SettingScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
    }
}
