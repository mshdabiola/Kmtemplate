/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.setting.detailscreen

import androidx.compose.runtime.Composable
import kotlinx.browser.window

@Composable
actual fun openUrl(url: String): () -> Unit {
    return {
        window.open(url, "_blank")
    }
}

@androidx.compose.runtime.Composable
actual fun openEmail(
    emailAddress: String,
    subject: String,
    body: String,
): () -> Unit {
    return {
        // Simple mailto link for Wasm/JS.
        // Encoding subject and body is important for special characters.
        val encodedSubject = kotlin.js.js("encodeURIComponent(subject)") as String
        val encodedBody = kotlin.js.js("encodeURIComponent(body)") as String
        window.open("mailto:$emailAddress?subject=$encodedSubject&body=$encodedBody", "_self")
    }
}
