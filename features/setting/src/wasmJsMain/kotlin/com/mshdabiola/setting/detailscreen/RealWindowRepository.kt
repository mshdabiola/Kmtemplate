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

import com.mshdabiola.setting.WindowRepository
import kotlinx.browser.window

// Top-level function for encoding URI components using JavaScript
fun encodeURIComponentJs(str: String): JsString = js("encodeURIComponent(str)")

class RealWindowRepository : WindowRepository {
    override fun openUrl(url: String) {
        window.open(url, "_blank")
    }

    override fun openEmail(emailAddress: String, subject: String, body: String) {
        // Simple mailto link for Wasm/JS.
        // Encoding subject and body is important for special characters.
        val encodedSubject = encodeURIComponentJs(subject) // Use the top-level function
        val encodedBody = encodeURIComponentJs(body) // Use the top-level function
        window.open("mailto:$emailAddress?subject=$encodedSubject&body=$encodedBody", "_self")
    }
}
