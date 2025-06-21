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
