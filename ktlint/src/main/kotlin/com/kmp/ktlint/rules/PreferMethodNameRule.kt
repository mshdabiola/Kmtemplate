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
            "MaterialTheme" to "NiaTheme",
            "Button" to "NiaButton",
            "OutlinedButton" to "NiaOutlinedButton",
            "TextButton" to "NiaTextButton",
            "FilterChip" to "NiaFilterChip",
            "ElevatedFilterChip" to "NiaFilterChip",
            "NavigationBar" to "NiaNavigationBar",
            "NavigationBarItem" to "NiaNavigationBarItem",
            "NavigationRail" to "NiaNavigationRail",
            "NavigationRailItem" to "NiaNavigationRailItem",
            "TabRow" to "NiaTabRow",
            "Tab" to "NiaTab",
            "IconToggleButton" to "NiaIconToggleButton",
            "FilledIconToggleButton" to "NiaIconToggleButton",
            "FilledTonalIconToggleButton" to "NiaIconToggleButton",
            "OutlinedIconToggleButton" to "NiaIconToggleButton",
            "CenterAlignedTopAppBar" to "NiaTopAppBar",
            "SmallTopAppBar" to "NiaTopAppBar",
            "MediumTopAppBar" to "NiaTopAppBar",
            "LargeTopAppBar" to "NiaTopAppBar",
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
