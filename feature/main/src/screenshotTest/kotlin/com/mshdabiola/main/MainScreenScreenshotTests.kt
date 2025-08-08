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
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.ThemePreviews
import com.mshdabiola.main.MainScreen
import com.mshdabiola.main.MainState
import com.mshdabiola.model.Note
import com.mshdabiola.ui.SharedTransitionContainer

@Preview(name = "MainScreen Loading State")
@Composable
internal fun MainScreenLoadingPreview() {
    SharedTransitionContainer {
        MainScreen(
            mainState = MainState.Loading,
            navigateToDetail = {},
        )
    }
}

@Preview(name = "MainScreen Empty State")
@Composable
internal fun MainScreenEmptyPreview() {
    SharedTransitionContainer {
        MainScreen(
            mainState = MainState.Empty,
            navigateToDetail = {},
        )
    }
}

@Preview(name = "MainScreen Success State")
@Composable
internal fun MainScreenSuccessPreview() {
    val sampleNotes = listOf(
        Note(id = 1, title = "First Note", content = "This is the content of the first note..."),
        Note(id = 2, title = "Second Note", content = "Content for the second one, a bit longer perhaps..."),
        Note(id = 3, title = "Third Note", content = "A short one."),
    )
    // KmtTheme {
    SharedTransitionContainer {
        MainScreen(
            mainState = MainState.Success(sampleNotes),
            navigateToDetail = {},
        )
    }
}

// If you have a custom @ThemePreviews annotation that applies your theme and device configs,
// you can use that instead of manually wrapping in KmtTheme.
// For example, if @ThemePreviews combines @Preview and light/dark themes:

@ThemePreviews // Assuming this applies necessary theming and preview configurations
@Composable
internal fun MainScreenLoadingThemedPreview() {
    SharedTransitionContainer {
        MainScreen(
            mainState = MainState.Loading,
            navigateToDetail = {},
        )
    }
}

@ThemePreviews
@Composable
internal fun MainScreenEmptyThemedPreview() {
    SharedTransitionContainer {
        MainScreen(
            mainState = MainState.Empty,
            navigateToDetail = {},
        )
    }
}

@ThemePreviews
@Composable
internal fun MainScreenSuccessThemedPreview() {
    val sampleNotes = listOf(
        Note(id = 1, title = "First Note", content = "This is the content of the first note..."),
        Note(id = 2, title = "Second Note", content = "Content for the second one, a bit longer perhaps..."),
        Note(id = 3, title = "Third Note", content = "A short one."),
    )
    SharedTransitionContainer {
        MainScreen(
            mainState = MainState.Success(sampleNotes),
            navigateToDetail = {},
        )
    }
}
