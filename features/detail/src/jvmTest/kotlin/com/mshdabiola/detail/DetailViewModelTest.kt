/*
 *abiola 2022
 */

package com.mshdabiola.detail

import com.mshdabiola.testing.repository.TestNoteRepository
import com.mshdabiola.testing.repository.TestUserDataRepository
import com.mshdabiola.testing.util.MainDispatcherRule
import com.mshdabiola.testing.util.TestAnalyticsHelper
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test

class DetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val analyticsHelper = TestAnalyticsHelper()
    private val userDataRepository = TestUserDataRepository()
    private val noteRepository = TestNoteRepository()

    // private val savedStateHandle = SavedStateHandle(mapOf(DETAIL_ID_ARG to 4))
    private lateinit var viewModel: DetailViewModel

    @BeforeTest
    fun setup() {
        viewModel = DetailViewModel(
            id = 0,
            noteRepository = noteRepository,
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
    }
}
