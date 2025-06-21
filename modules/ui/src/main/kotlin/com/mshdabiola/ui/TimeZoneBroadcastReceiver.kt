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
package com.mshdabiola.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class TimeZoneBroadcastReceiver(
    val onTimeZoneChanged: () -> Unit,
) : BroadcastReceiver() {
    private var registered = false

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == Intent.ACTION_TIMEZONE_CHANGED) {
            onTimeZoneChanged()
        }
    }

    fun register(context: Context) {
        if (!registered) {
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED)
            context.registerReceiver(this, filter)
            registered = true
        }
    }

    fun unregister(context: Context) {
        if (registered) {
            context.unregisterReceiver(this)
            registered = false
        }
    }
}
