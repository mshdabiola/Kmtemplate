package com.kmp.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.psiUtil.getParentOfType

class StringLiteralRule : Rule(RuleId(Constants.RAW_STRING_RULE_ID), About()) {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        val text = node.text
        val shouldEmit =
            when {
                node.elementType != ElementType.STRING_TEMPLATE -> false
                text == Constants.EMPTY_DOUBLE_QUOTE || text == Constants.EMPTY_TRIPLE_QUOTE -> false
                node.psi.getParentOfType<KtProperty>(false)
                    ?.hasModifier(KtTokens.CONST_KEYWORD) == true -> false

                (node.psi as? KtStringTemplateExpression)?.let { psi ->
                    psi.entries.any { it.expression != null } || psi.getParentOfType<KtAnnotationEntry>(
                        true,
                    ) != null
                } ?: true -> false

                else -> true
            }

        if (shouldEmit) {
            emit(
                node.startOffset,
                Constants.RAW_STRING_RULE_DESCRIPTION,
                false,
            )
        }
    }
}
