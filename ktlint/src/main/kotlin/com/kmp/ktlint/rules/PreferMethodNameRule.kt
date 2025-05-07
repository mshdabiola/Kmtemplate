package com.kmp.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtCallExpression

class PreferMethodNameRule : Rule(
    RuleId("hydraulic:prefer-method-name"),
    about =
        Rule.About(
            maintainer = "Your Name",
            repositoryUrl = "https://github.com/mshdabiola/hydraulic",
            issueTrackerUrl = "https://github.com/mshdabiola/hydraulic",
        ),
) {
    // Define the mapping of deprecated method names to preferred method names
    private val methodNameReplacements =
        mapOf(
            "MaterialTheme" to "HyaTheme",
            "Button" to "HyaButton",
            "OutlinedButton" to "HyaOutlinedButton",
            "TextButton" to "HyaTextButton",
            "FilterChip" to "HyaFilterChip",
            "ElevatedFilterChip" to "HyaFilterChip",
            "NavigationBar" to "HyaNavigationBar",
            "NavigationBarItem" to "HyaNavigationBarItem",
            "NavigationRail" to "HyaNavigationRail",
            "NavigationRailItem" to "HyaNavigationRailItem",
            "TabRow" to "HyaTabRow",
            "Tab" to "HyaTab",
            "IconToggleButton" to "HyaIconToggleButton",
            "FilledIconToggleButton" to "HyaIconToggleButton",
            "FilledTonalIconToggleButton" to "HyaIconToggleButton",
            "OutlinedIconToggleButton" to "HyaIconToggleButton",
            "CenterAlignedTopAppBar" to "HyaTopAppBar",
            "SmallTopAppBar" to "HyaTopAppBar",
            "MediumTopAppBar" to "HyaTopAppBar",
            "LargeTopAppBar" to "HyaTopAppBar",
        )

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeFixed: Boolean) -> Unit,
    ) {
        // Check if the current node is a method call expression
        if (node.psi is KtCallExpression) {
            val callExpression = node.psi as KtCallExpression
            val methodName = callExpression.calleeExpression?.text

            // Check if the method name is in our mapping
            if (methodName != null && methodNameReplacements.containsKey(methodName)) {
                val preferredName = methodNameReplacements[methodName]
                val errorMessage =
                    "Using '$methodName' instead of '$preferredName'. Consider using '$preferredName'."

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
