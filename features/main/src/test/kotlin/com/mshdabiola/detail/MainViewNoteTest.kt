/*
 *abiola 2022
 */

package com.mshdabiola.detail

import com.mshdabiola.data.fake.FakeNoteRepository
import com.mshdabiola.main.MainViewModel
import com.mshdabiola.testing.repository.TestUserDataRepository
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewNoteTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val userDataRepository = TestUserDataRepository()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(
            userDataRepository = userDataRepository,
            modelRepository = FakeNoteRepository(),

        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        //  assertEquals(Loading, viewModel.feedUiMainState.value)
    }

    @Test
    fun oneBookmark_showsInFeed() = runTest {
        // val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.feedUiMainState.collect() }
//
//        newsRepository.sendNewsResources(newsResourcesTestData)
//        userDataRepository.updateNewsResourceBookmark(newsResourcesTestData[0].id, true)
//        val item = viewModel.feedUiMainState.value
//        assertIs<Success>(item)
//        assertEquals(item.feed.size, 1)
//
//        collectJob.cancel()
    }
}
