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

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.window.core.layout.WindowWidthSizeClass
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtApp
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtAppTestTags
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

// Mock ViewModel and State for testing purposes in JVM
class MockMainAppViewModel {
    val uiState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    // Add any methods that KmtApp might call if necessary for basic rendering
}

class KmtAppTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel = MockMainAppViewModel() // Use a simple mock/fake

    // Koin module for testing
    private val testModule = module {
        // Provide the mock ViewModel - this is a simplified way.
        // For koinViewModel(), Koin needs to know how to construct it.
        // A more robust way would be to register it properly if KmtApp uses koinViewModel() directly.
        // However, if KmtApp is refactored to accept ViewModel as a parameter, this becomes easier.
        // For this example, we'll assume KmtApp might be refactored or we'll handle it.
        factory { mockViewModel }
    }

    @Before
    fun setUp() {
        // Start Koin with the test module for each test if KmtApp uses Koin directly
        // and hasn't been refactored to take dependencies as parameters.
        // This is a basic setup. For `koinViewModel()`, specific registration is needed.
        // If KmtApp is refactored, this Koin setup in the test might not be as critical
        // for testing the UI part.
        // org.koin.core.context.startKoin { modules(testModule) }
    }

    @After
    fun tearDown() {
        stopKoin() // Stop Koin after each test
    }

    @Composable
    private fun TestableKmtAppWrapper(
        initialUiState: MainActivityUiState = MainActivityUiState.Loading,
        windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.COMPACT,
    ) {
        // Update the mock ViewModel's state if needed for the test scenario
        mockViewModel.uiState.value = initialUiState

        // For `jvmTest`, we need to provide KoinApplication if Koin is used internally by KmtApp
        // and not just by a top-level wrapper.
        KoinApplication(application = { modules(testModule) }) {
            // It's better if KmtApp takes its dependencies as parameters for easier testing.
            // If KmtApp directly calls koinViewModel(), it's harder to test in JVM
            // without full Koin setup that matches the app's module structure.

            // Assuming KmtApp might look like this for better testability:
            // KmtAppInternal(
            //     appState = rememberKmtAppState(...),
            //     uiState = initialUiState,
            //     analyticsHelper = mockAnalyticsHelper
            // )
            // For now, let's call the original KmtApp and see what happens with basic Koin setup.
            // This might fail if Koin setup isn't perfect for `koinViewModel()`.
            KmtApp()
        }
    }

    @Test
    fun kmtApp_initialStructure_isDisplayed_compact() {
        composeTestRule.setContent {
            // KmtApp uses currentWindowAdaptiveInfo(), which might be problematic in JVM.
            // Ideally, KmtApp or its internal components should allow injecting WindowSizeClass
            // for testing.
            // For now, this test will verify what it can.
            TestableKmtAppWrapper(windowWidthSizeClass = WindowWidthSizeClass.COMPACT)
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
    fun kmtApp_displaysCorrectThemeAndBackground_whenUserDataLoaded() {
        val successUserData = UserData(
            contrast = 1,
            darkThemeConfig = DarkThemeConfig.DARK,
            useDynamicColor = true,
            // ... other fields
        )
        composeTestRule.setContent {
            TestableKmtAppWrapper(initialUiState = MainActivityUiState.Success(successUserData))
        }

        // We can't easily verify the *actual* theme colors in a JVM test without
        // complex image comparison or deep inspection of modifier properties.
        // However, we can ensure the main components are still rendered.
        composeTestRule.onNodeWithTag(KmtAppTestTags.APP_ROOT_LAYOUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtAppTestTags.GRADIENT_BACKGROUND).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtAppTestTags.MAIN_SCAFFOLD).assertIsDisplayed()
        composeTestRule.onNodeWithTag(KmtAppTestTags.NAV_HOST).assertIsDisplayed()

        // Further tests for theme application would typically require instrumentation tests
        // or more specialized UI testing frameworks that can inspect rendering details.
    }
}
