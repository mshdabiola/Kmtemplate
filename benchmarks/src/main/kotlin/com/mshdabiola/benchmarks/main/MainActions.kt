/*
 * Copyright (C) 2024-2025 MshdAbiola
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

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.mshdabiola.benchmarks.flingElementDownUp
import com.mshdabiola.model.testtag.KmtScaffoldTestTags
import com.mshdabiola.model.testtag.MainScreenTestTags

fun MacrobenchmarkScope.goToDetailScreen() {
    val savedSelector = By.res(KmtScaffoldTestTags.FabTestTags.EXTENDED_FAB)

    device.wait(Until.hasObject(savedSelector), 5000)

    val addButton = device.findObject(savedSelector)
    addButton.click()
    device.waitForIdle()
    // Wait until saved title are shown on screen
}

fun MacrobenchmarkScope.mainScrollNoteDownUp() {
    val selector = By.res(MainScreenTestTags.NOTE_LIST)
    device.wait(Until.hasObject(selector), 5000)

    val feedList = device.findObject(selector)
    device.flingElementDownUp(feedList)
}

fun MacrobenchmarkScope.mainWaitForContent() {
    // Wait until content is loaded by checking if topics are loaded
    //  device.wait(Until.gone(By.res("loadingWheel")), 5_000)
    // Sometimes, the loading wheel is gone, but the content is not loaded yet
    // So we'll wait here for topics to be sure
    //   val obj = device.waitAndFindObject(By.res("forYou:topicSelection"), 10_000)
    // Timeout here is quite big, because sometimes data loading takes a long time!
    //   obj.wait(untilHasChildren(), 60_000)
}

fun MacrobenchmarkScope.setAppTheme(isDark: Boolean) {
    when (isDark) {
        true -> device.findObject(By.text("Dark")).click()
        false -> device.findObject(By.text("Light")).click()
    }
    device.waitForIdle()
    device.findObject(By.text("OK")).click()

    // Wait until the top app bar is visible on screen
//    waitForObjectOnTopAppBar(By.text("Now in Android"))
}
