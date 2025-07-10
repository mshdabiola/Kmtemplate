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
    compileOnly(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)
    compileOnly("com.pinterest.ktlint:ktlint-rule-engine:1.0.0")
    compileOnly("com.pinterest.ktlint:ktlint-ruleset-standard:1.6.0")
    testImplementation("com.pinterest.ktlint:ktlint-test:1.6.0")
}
