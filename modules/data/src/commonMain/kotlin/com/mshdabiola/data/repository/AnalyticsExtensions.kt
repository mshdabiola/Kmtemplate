/*
 * Copyright (C) 2024-2025 MshdAbiola
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
package com.mshdabiola.data.repository

import com.mshdabiola.analytics.AnalyticsEvent
import com.mshdabiola.analytics.AnalyticsHelper

internal fun AnalyticsHelper.logNewsResourceBookmarkToggled(
    newsResourceId: String,
    isBookmarked: Boolean,
) {
    val eventType = if (isBookmarked) "news_resource_saved" else "news_resource_unsaved"
    val paramKey = if (isBookmarked) "saved_news_resource_id" else "unsaved_news_resource_id"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras =
            listOf(
                AnalyticsEvent.Param(key = paramKey, value = newsResourceId),
            ),
        ),
    )
}

internal fun AnalyticsHelper.logTopicFollowToggled(
    followedTopicId: String,
    isFollowed: Boolean,
) {
    val eventType = if (isFollowed) "topic_followed" else "topic_unfollowed"
    val paramKey = if (isFollowed) "followed_topic_id" else "unfollowed_topic_id"
    logEvent(
        AnalyticsEvent(
            type = eventType,
            extras =
            listOf(
                AnalyticsEvent.Param(key = paramKey, value = followedTopicId),
            ),
        ),
    )
}

internal fun AnalyticsHelper.logThemeChanged(themeName: String) =
    logEvent(
        AnalyticsEvent(
            type = "theme_changed",
            extras =
            listOf(
                AnalyticsEvent.Param(key = "theme_name", value = themeName),
            ),
        ),
    )

internal fun AnalyticsHelper.logContrastChanged(contrastName: String) =
    logEvent(
        AnalyticsEvent(
            type = "Contrast_changed",
            extras =
            listOf(
                AnalyticsEvent.Param(key = "theme_name", value = contrastName),
            ),
        ),
    )

internal fun AnalyticsHelper.logDarkThemeConfigChanged(darkThemeConfigName: String) =
    logEvent(
        AnalyticsEvent(
            type = "dark_theme_config_changed",
            extras =
            listOf(
                AnalyticsEvent.Param(key = "dark_theme_config", value = darkThemeConfigName),
            ),
        ),
    )

internal fun AnalyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor: Boolean) =
    logEvent(
        AnalyticsEvent(
            type = "dynamic_color_preference_changed",
            extras =
            listOf(
                AnalyticsEvent.Param(
                    key = "dynamic_color_preference",
                    value = useDynamicColor.toString(),
                ),
            ),
        ),
    )

internal fun AnalyticsHelper.logOnboardingStateChanged(shouldHideOnboarding: Boolean) {
    val eventType = if (shouldHideOnboarding) "onboarding_complete" else "onboarding_reset"
    logEvent(
        AnalyticsEvent(type = eventType),
    )
}
