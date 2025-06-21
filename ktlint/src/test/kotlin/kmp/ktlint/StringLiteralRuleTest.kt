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
package kmp.ktlint

import com.kmp.ktlint.rules.Constants
import com.kmp.ktlint.rules.PreferReceiverNameRule
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class StringLiteralRuleTest {
    private val stringRuleAssertThat = assertThatRule { PreferReceiverNameRule() }

    @Test
    fun `should report error on raw string literal in function`() {
        val code =
            """
            fun greet() {
                println("Hello, world!")
            }
            """.trimIndent()

        stringRuleAssertThat(code)
            .hasLintViolationWithoutAutoCorrect(
                2,
                13,
                Constants.RAW_STRING_RULE_DESCRIPTION,
            )
    }

    @Test
    fun `should not report error on const val`() {
        val code =
            """
            const val GREETING = "Hello, world!"
            
            fun greet() {
                println(GREETING)
            }
            """.trimIndent()

        stringRuleAssertThat(code).hasNoLintViolations()
    }
}
