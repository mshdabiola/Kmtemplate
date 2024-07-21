/*
 *abiola 2022
 */

package com.mshdabiola.setting

import com.mshdabiola.testing.repository.TestNoteRepository
import com.mshdabiola.testing.repository.TestUserDataRepository
import com.mshdabiola.testing.util.MainDispatcherRule
import com.mshdabiola.testing.util.TestAnalyticsHelper
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * To learn more about how this test handles Flows created with stateIn, see
 * https://developer.android.com/kotlin/flow/test#statein
 */
class SettingViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val analyticsHelper = TestAnalyticsHelper()
    private val userDataRepository = TestUserDataRepository()
    private val noteRepository = TestNoteRepository()

//    private val savedStateHandle = SavedStateHandle(mapOf(DETAIL_ID_ARG to 4))
    private lateinit var viewModel: SettingViewModel

    @Before
    fun setup() {
        viewModel = SettingViewModel(
            userDataRepository = userDataRepository,
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
    }
}
