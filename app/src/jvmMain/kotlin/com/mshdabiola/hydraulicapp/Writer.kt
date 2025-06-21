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
package com.mshdabiola.hydraulicapp

import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Writer(private val path: File) : LogWriter() {
    private val filePath: File by lazy {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formatter.format(Date())
        File(
            File(path, "log").apply {
                if (this.exists().not()) {
                    mkdirs()
                }
            },
            "log-$date.txt",
        )
    }

    override fun log(
        severity: Severity,
        message: String,
        tag: String,
        throwable: Throwable?,
    ) {
        val messageStr = DefaultFormatter.formatMessage(severity, Tag(tag), Message(message))
        saveLogsToTxtFile("$messageStr \n")
        if (throwable != null) {
            saveLogsToTxtFile("\n\n ${throwable.localizedMessage}")
        }
    }

    private fun saveLogsToTxtFile(message: String) {
        val coroutineCallLogger = CoroutineScope(Dispatchers.IO)
        coroutineCallLogger.launch {
            runCatching {
                if (filePath.exists().not()) {
                    filePath.createNewFile()
                }
                // Writing my logs to txt file.
                filePath.appendText(
                    message,
                )
            }
        }
    }
}
