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
import com.kmp.ktlint.rules.Constants
import com.kmp.ktlint.rules.PreferReceiverNameRule
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class MagicNumberRuleTest {
    private val magicNumberRuleAssertThat = assertThatRule {PreferReceiverNameRule()}

    @Test
    fun `should report error when magic number is used`() {
        val code =
            """
            fun calculate() {
                val result = 42
                println(result)
            }
            """.trimIndent()
        val line = 2
        val column = 18
        magicNumberRuleAssertThat(code)
            .hasLintViolationWithoutAutoCorrect(
                line,
                column,
                Constants.MAGIC_NUMBERS_RULE_DESCRIPTION,
            )
    }

    @Test
    fun `should not report error when allowed number is used`() {
        val code =
            """
            fun calculate() {
                val defaultValue = 1
                println(defaultValue)
            }
            """.trimIndent()

        magicNumberRuleAssertThat(code).hasNoLintViolations()
    }
}
