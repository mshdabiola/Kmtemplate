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
package androidx.test.uiautomator

import androidx.test.uiautomator.HasChildrenOp.AT_LEAST
import androidx.test.uiautomator.HasChildrenOp.AT_MOST
import androidx.test.uiautomator.HasChildrenOp.EXACTLY

// These helpers need to be in the androidx.test.uiautomator package,
// because the abstract class has package local method that needs to be implemented.

/**
 * Condition will be satisfied if given element has specified count of children
 */
fun untilHasChildren(
    childCount: Int = 1,
    op: HasChildrenOp = AT_LEAST,
): UiObject2Condition<Boolean> =
    object : UiObject2Condition<Boolean>() {
        override fun apply(element: UiObject2): Boolean =
            when (op) {
                AT_LEAST -> element.childCount >= childCount
                EXACTLY -> element.childCount == childCount
                AT_MOST -> element.childCount <= childCount
            }
    }

enum class HasChildrenOp {
    AT_LEAST,
    EXACTLY,
    AT_MOST,
}
