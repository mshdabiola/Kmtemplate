package kmp.ktlint

import com.kmp.ktlint.rules.Constants
import com.kmp.ktlint.rules.StringLiteralRule
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class StringLiteralRuleTest {
    private val stringRuleAssertThat = assertThatRule { StringLiteralRule() }

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
