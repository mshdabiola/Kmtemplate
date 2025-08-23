/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.testing.util

import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.TestConfig
import co.touchlab.kermit.TestLogWriter
import co.touchlab.kermit.platformLogWriter

@OptIn(ExperimentalKermitApi::class)
val testLogger = Logger(
    TestConfig(
        minSeverity = Severity.Verbose,
        logWriterList = listOf(
            TestLogWriter(
                loggable = Severity.Verbose,
            ),
            platformLogWriter(DefaultFormatter),
        ),
    ),
)
