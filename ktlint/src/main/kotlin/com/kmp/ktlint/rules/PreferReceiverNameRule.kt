package com.kmp.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression

class PreferReceiverNameRule : Rule(
    RuleId("hydraulic:prefer-receiver-name"),
    about =
        Rule.About(
            maintainer = "Your Name",
            repositoryUrl = "https://github.com/mshdabiola/hydraulic",
            issueTrackerUrl = "https://github.com/mshdabiola/hydraulic",
        ),
) {
    // Define the mapping of deprecated receiver names to preferred receiver names
    private val stringStringMap =
        mapOf(
            "Icons" to "NiaIcons",
        )

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeFixed: Boolean) -> Unit,
    ) {
        // Check if the current node is a dot-qualified expression (e.g., receiver.method())
        if (node.psi is KtDotQualifiedExpression) {
            val qualifiedExpression = node.psi as KtDotQualifiedExpression
            val receiverExpression = qualifiedExpression.receiverExpression
            val receiverName = receiverExpression.text

            // Check if the receiver name is in our mapping
            if (stringStringMap.containsKey(receiverName)) {
                val preferredName = stringStringMap[receiverName]
                val errorMessage =
                    "Using '$receiverName' as receiver instead of '$preferredName'. Consider using '$preferredName'."

                // Report the violation
                emit(
                    node.startOffset,
                    errorMessage,
                    false,
                )
            }
        }
    }
}
// Auto-correction for receiver names can be complex
