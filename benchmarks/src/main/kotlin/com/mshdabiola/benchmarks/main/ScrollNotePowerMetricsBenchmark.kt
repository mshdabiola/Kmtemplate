/*
 * Copyright (C) 2023-2025 MshdAbiola
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
package com.mshdabiola.benchmarks.main

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.PowerCategory
import androidx.benchmark.macro.PowerCategoryDisplayLevel
import androidx.benchmark.macro.PowerMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import com.mshdabiola.benchmarks.PACKAGE_NAME
import com.mshdabiola.benchmarks.allowNotifications
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalMetricApi::class)
@RequiresApi(VERSION_CODES.Q)
@RunWith(AndroidJUnit4::class)
class ScrollNotePowerMetricsBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    private val categories =
        PowerCategory.entries
            .associateWith { PowerCategoryDisplayLevel.TOTAL }

    @Test
    fun benchmarkStateChangeCompilationLight() = benchmarkStateChangeWithTheme(CompilationMode.Partial(), false)

    @Test
    fun benchmarkStateChangeCompilationDark() = benchmarkStateChangeWithTheme(CompilationMode.Partial(), true)

    private fun benchmarkStateChangeWithTheme(
        compilationMode: CompilationMode,
        isDark: Boolean,
    ) = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric(), PowerMetric(PowerMetric.Energy(categories))),
        compilationMode = compilationMode,
        iterations = 2,
        startupMode = StartupMode.WARM,
        setupBlock = {
            // Start the app
            pressHome()
            startActivityAndWait()
            allowNotifications()
            // Navigate to Settings
            device.findObject(By.desc("Settings")).click()
            device.waitForIdle()
            setAppTheme(isDark)
        },
    ) {
        mainScrollNoteDownUp()
    }
}
