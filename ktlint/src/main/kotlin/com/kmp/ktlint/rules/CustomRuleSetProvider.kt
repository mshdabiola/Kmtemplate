package com.kmp.ktlint.rules

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

class CustomRuleSetProvider :
    RuleSetProviderV3(RuleSetId(Constants.CUSTOM_RULES_GROUP)) {
    override fun getRuleProviders(): Set<RuleProvider> {
        return setOf(
            RuleProvider { MagicNumberRule() },
            RuleProvider { StringLiteralRule() },
        )
    }
}
