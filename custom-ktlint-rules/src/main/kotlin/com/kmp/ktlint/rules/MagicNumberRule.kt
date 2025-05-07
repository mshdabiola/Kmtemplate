package com.kmp.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.getParentOfType

class MagicNumberRule : Rule(RuleId(Constants.MAGIC_NUMBERS_RULE_ID), About()) {
    private val allowedNumbers =
        setOf(
            Constants.ALLOWED_INT_ZERO,
            Constants.ALLOWED_INT_ONE,
            Constants.ALLOWED_INT_MINUS_ONE,
            Constants.ALLOWED_FLOAT_ONE,
        )

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        if (node.elementType == ElementType.INTEGER_CONSTANT || node.elementType == ElementType.FLOAT_CONSTANT) {
            val text = node.text

            val property = node.psi.getParentOfType<KtProperty>(false)
            if (property?.hasModifier(KtTokens.CONST_KEYWORD) == true) {
                return
            }

            val callExpression = node.psi.getParentOfType<KtCallExpression>(true)
            val isColorCall = callExpression?.calleeExpression?.text == Constants.COLOR_CALL_NAME
            val isHexColorLiteral = text.startsWith(Constants.HEX_COLOR_PREFIX, ignoreCase = true)

            if (isColorCall && isHexColorLiteral) {
                return
            }

            if (text !in allowedNumbers) {
                emit(
                    node.startOffset,
                    Constants.MAGIC_NUMBERS_RULE_DESCRIPTION,
                    false,
                )
            }
        }
    }
}
