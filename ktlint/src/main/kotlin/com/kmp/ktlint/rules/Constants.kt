package com.kmp.ktlint.rules

object Constants {
    const val TODO = "TODO"
    const val TODO_RULE_ID = "custom:todo-rule"
    const val TODO_RULE_DESCRIPTION = "TODO comments are not allowed."
    const val MAGIC_NUMBERS_RULE_ID = "custom:magic-numbers-rule"
    const val MAGIC_NUMBERS_RULE_DESCRIPTION =
        "Magic numbers are not allowed. Use constants instead."
    const val RAW_STRING_RULE_ID = "custom:raw-string-rule"
    const val RAW_STRING_RULE_DESCRIPTION = "Avoid using raw string literals. Use const val instead."
    const val CUSTOM_RULES_GROUP = "custom-ktlint-rules"

    const val EMPTY_DOUBLE_QUOTE = "\"\""
    const val EMPTY_TRIPLE_QUOTE = "\"\"\"\"\"\""
    const val ALLOWED_INT_ZERO = "0"
    const val ALLOWED_INT_ONE = "1"
    const val ALLOWED_INT_MINUS_ONE = "-1"
    const val ALLOWED_FLOAT_ONE = "1f"
    const val COLOR_CALL_NAME = "Color"
    const val HEX_COLOR_PREFIX = "0xFF"
}
