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
package com.mshdabiola.designsystem.drawable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon: ImageVector
    get() {
        if (_AppIcon != null) {
            return _AppIcon!!
        }
        _AppIcon = ImageVector.Builder(
            name = "AppIcon",
            defaultWidth = 128.58.dp,
            defaultHeight = 176.dp,
            viewportWidth = 128.58f,
            viewportHeight = 176f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12f, 0f)
                horizontalLineTo(116.58f)
                arcToRelative(12f, 12f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 12f)
                verticalLineTo(25f)
                arcToRelative(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0f)
                horizontalLineTo(0f)
                arcToRelative(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0f)
                verticalLineTo(12f)
                arcTo(12f, 12f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 0f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(0f, 32f)
                horizontalLineTo(40.58f)
                arcToRelative(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0f)
                verticalLineTo(176f)
                arcToRelative(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0f)
                horizontalLineTo(11.91f)
                arcTo(11.91f, 11.91f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 164.09f)
                verticalLineTo(32f)
                arcTo(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 32f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(48.58f, 32f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(60f)
                horizontalLineToRelative(-80f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(48.58f, 100f)
                horizontalLineToRelative(80f)
                arcToRelative(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0f)
                verticalLineToRelative(63.69f)
                arcTo(12.31f, 12.31f, 0f, isMoreThanHalf = false, isPositiveArc = true, 116.26f, 176f)
                horizontalLineTo(48.58f)
                arcToRelative(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0f)
                verticalLineTo(100f)
                arcTo(0f, 0f, 0f, isMoreThanHalf = false, isPositiveArc = true, 48.58f, 100f)
                close()
            }
        }.build()

        return _AppIcon!!
    }

@Suppress("ObjectPropertyName")
private var _AppIcon: ImageVector? = null
