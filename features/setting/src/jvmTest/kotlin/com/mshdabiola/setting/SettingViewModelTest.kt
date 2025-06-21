/*
 * Copyright (C) 2022-2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.setting

import app.cash.turbine.test
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.testing.fake.testDataModule
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SettingViewModelTest : KoinTest {
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule(order = 3)
    val koinTestRule =
        KoinTestRule.create {
            this.modules(testDataModule)
        }
    private val userRepository by inject<UserDataRepository>()

    @Test
    fun init() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel =
                SettingViewModel(
                    userDataRepository = userRepository,
                )

            viewModel
                .settingState
                .test {
                    var state = awaitItem()

                    assertTrue(state is SettingState.Loading)

                    state = awaitItem()

                    assertTrue(state is SettingState.Success)

                    assertEquals(
                        SettingState.Success(
                            themeBrand = com.mshdabiola.model.ThemeBrand.DEFAULT,
                            darkThemeConfig = com.mshdabiola.model.DarkThemeConfig.DARK,
                        ),
                        state,
                    )

                    cancelAndIgnoreRemainingEvents()
                }
        }

    @Test
    fun setThemeTest() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel =
                SettingViewModel(
                    userDataRepository = userRepository,
                )

            viewModel
                .settingState
                .test {
                    var state = awaitItem()

                    assertTrue(state is SettingState.Loading)

                    state = awaitItem()

                    assertTrue(state is SettingState.Success)

                    viewModel.setThemeBrand(com.mshdabiola.model.ThemeBrand.GREEN)

                    state = awaitItem()

                    assertTrue(state is SettingState.Loading)

                    state = awaitItem()

                    assertTrue(state is SettingState.Success)

                    assertEquals(state.themeBrand, com.mshdabiola.model.ThemeBrand.GREEN)

                    cancelAndIgnoreRemainingEvents()
                }
        }

    @Test
    fun setDarkTest() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel =
                SettingViewModel(
                    userDataRepository = userRepository,
                )

            viewModel
                .settingState
                .test {
                    var state = awaitItem()

                    assertTrue(state is SettingState.Loading)

                    state = awaitItem()

                    assertTrue(state is SettingState.Success)

                    viewModel.setDarkThemeConfig(com.mshdabiola.model.DarkThemeConfig.LIGHT)

                    state = awaitItem()

                    assertTrue(state is SettingState.Loading)

                    state = awaitItem()

                    assertTrue(state is SettingState.Success)

                    assertEquals(DarkThemeConfig.LIGHT, state.darkThemeConfig)

                    cancelAndIgnoreRemainingEvents()
                }
        }
}
