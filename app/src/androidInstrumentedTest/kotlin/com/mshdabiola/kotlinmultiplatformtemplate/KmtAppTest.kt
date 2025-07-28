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

// import com.mshdabiola.designsystem.drawable.KmtDrawable
// import com.mshdabiola.designsystem.strings.KmtStrings
// import com.mshdabiola.detail.detailModule
// import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtAppTestTags
// import com.mshdabiola.kotlinmultiplatformtemplate.ui.SplashScreen
// import com.mshdabiola.main.mainModule
// import com.mshdabiola.model.getLoggerWithTag
// import com.mshdabiola.setting.settingModule
// import com.mshdabiola.testing.fake.testDataModule
// import com.mshdabiola.testing.util.testLogger
// import kotlinx.coroutines.delay
// import org.junit.After
// import org.junit.Before
// import org.junit.Rule
// import org.junit.Test
// import org.koin.core.context.startKoin
// import org.koin.core.context.stopKoin
// import org.koin.core.scope.Scope
// import org.koin.dsl.module
import org.koin.test.KoinTest

class KmtAppTest : KoinTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    val appModule =
//        module {
//            includes(testDataModule, detailModule, mainModule, settingModule)
//            viewModel {
//                MainAppViewModel(
//                    Scope.get(),
//                    getLoggerWithTag("MainAppViewModel"),
//                )
//            }
//        }
//
//    @Before
//    fun init() {
//        startKoin {
//            logger(
//                KermitKoinLogger(Logger.withTag("koin")),
//            )
//
//            modules(
//                appModule,
//                kermitLoggerModule(testLogger),
//            )
//        }
//    }
//
//    @After
//    fun tearDown() {
//        stopKoin() // Stop Koin after each test
//    }
//
//    @Composable
//    fun KmtApp(windowState: WindowState) {
//        Window(
//            onCloseRequest = {},
//            title = "${KmtStrings.brand} v${KmtStrings.version}",
//            icon = KmtDrawable.brandImage,
//            state = windowState,
//        ) {
//            val show = remember { mutableStateOf(true) }
//            LaunchedEffect(Unit) {
//                delay(2000)
//                show.value = false
//            }
//            Box(Modifier.fillMaxSize()) {
//                com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtApp()
//                if (show.value) {
//                    SplashScreen()
//                }
//            }
//        }
//    }
//
//    @Test
//    fun kmtApp_initialStructure_isDisplayed_compact() {
//        composeTestRule.setContent {
//            val windowState =
//                rememberWindowState(
//                    size = DpSize(width = 1100.dp, height = 600.dp),
//                    placement = WindowPlacement.Maximized,
//                    position = WindowPosition.Aligned(Alignment.Center),
//                )
//            KmtApp(windowState)
//        }
//
//        // Check for the root layout
//        composeTestRule.onNodeWithTag(KmtAppTestTags.APP_ROOT_LAYOUT)
//            .assertExists("App root layout should exist")
//            .assertIsDisplayed()
//
//        // Check for the gradient background
//        composeTestRule.onNodeWithTag(KmtAppTestTags.GRADIENT_BACKGROUND)
//            .assertExists("Gradient background should exist")
//            .assertIsDisplayed()
//
//        // Check for the main scaffold
//        composeTestRule.onNodeWithTag(KmtAppTestTags.MAIN_SCAFFOLD)
//            .assertExists("Main scaffold should exist")
//            .assertIsDisplayed()
//
//        // Check for the NavHost
//        composeTestRule.onNodeWithTag(KmtAppTestTags.NAV_HOST)
//            .assertExists("NavHost should exist")
//            .assertIsDisplayed()
//    }
}
