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
plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    implementation(libs.slf4j.simple)
    compileOnly(libs.kotlin.stdlib) // Keep this as compileOnly if it's provided by the Kotlin plugin
    testImplementation(libs.kotlin.test)
    compileOnly(libs.ktlint.rule.engine) // If your main code also implements rules
    compileOnly(libs.ktlint.ruleset.standard) // If your main code also implements rules
    testImplementation(libs.ktlint.rule.engine)
    testImplementation(libs.ktlint.ruleset.standard)

    testImplementation(libs.ktlint.test)
}
