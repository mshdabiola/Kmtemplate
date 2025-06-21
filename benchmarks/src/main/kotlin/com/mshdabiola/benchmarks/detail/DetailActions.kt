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
package com.mshdabiola.benchmarks.detail

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.goBack() {
    val selector = By.res("back")

    device.wait(Until.hasObject(selector), 5000)

    val backButton = device.findObject(selector)
    backButton.click()
//    device.waitForIdle()
    device.waitForIdle(1000)
    // Wait until saved title are shown on screen
}

fun MacrobenchmarkScope.addNote() {
    val titleSelector = By.res("detail:title")
    val contentSelector = By.res("detail:content")

    device.wait(Until.hasObject(titleSelector), 5000)

    val titleTextField = device.findObject(titleSelector)
    val contentTextField = device.findObject(contentSelector)

    titleTextField.text = "title"
    contentTextField.text = "content"

    // Wait until saved title are shown on screen
}
