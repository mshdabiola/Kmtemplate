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
package com.mshdabiola.analytics

/**
 * An implementation of AnalyticsHelper just writes the events to logcat. Used in builds where no
 * analytics events should be sent to a backend.
 */

internal class StubAnalyticsHelper : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
//        Log.d(TAG, "Received analytics event: $event")
    }
}
