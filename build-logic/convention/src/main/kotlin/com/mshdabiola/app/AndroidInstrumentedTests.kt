/*
 * Copyright (C) 2025 MshdAbiola
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
package com.mshdabiola.app

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project

/**
 * Disable unnecessary Android instrumented tests for the [project] if there is no `androidTest` folder.
 * Otherwise, these projects would be compiled, packaged, installed and ran only to end-up with the following message:
 *
 * > Starting 0 tests on AVD
 *
 * Note: this could be improved by checking other potential sourceSets based on buildTypes and flavors.
 */
internal fun LibraryAndroidComponentsExtension.disableUnnecessaryAndroidTests(
    project: Project,
) = beforeVariants {
    it.enableAndroidTest = it.enableAndroidTest &&
        project.projectDir.resolve("src/androidTest").exists()
}
