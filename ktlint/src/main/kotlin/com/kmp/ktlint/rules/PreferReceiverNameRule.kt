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
package com.kmp.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression

class PreferReceiverNameRule : Rule(
    RuleId("hydraulicapp:prefer-receiver-name"),
    about =
    Rule.About(
        maintainer = "Your Name",
        repositoryUrl = "https://github.com/mshdabiola/hydraulicapp",
        issueTrackerUrl = "https://github.com/mshdabiola/hydraulicapp",
    ),
) {
    // Define the mapping of deprecated receiver names to preferred receiver names
    private val stringStringMap =
        mapOf(
            "Icons" to "HyaIcons",
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
