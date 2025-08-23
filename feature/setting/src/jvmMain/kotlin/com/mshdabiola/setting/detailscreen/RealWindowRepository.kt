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
package com.mshdabiola.setting.detailscreen

import com.mshdabiola.setting.WindowRepository
import java.awt.Desktop
import java.net.URI

class RealWindowRepository : WindowRepository {
    override fun openUrl(url: String) {
        val desktop = Desktop.getDesktop()
        if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI(url))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            println("Desktop browse action not supported.")
        }
    }

    override fun openEmail(emailAddress: String, subject: String, body: String) {
        val desktop = Desktop.getDesktop()
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    val mailtoUri = "mailto:$emailAddress?subject=${
                        java.net.URLEncoder.encode(subject, "UTF-8")
                    }&body=${java.net.URLEncoder.encode(body, "UTF-8")}"
                    desktop.mail(URI(mailtoUri))
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Fallback or error handling
                    println("Error opening email client: ${e.message}")
                }
            } else {
                println("Desktop.Action.MAIL is not supported.")
                // You might try opening a mailto: link in the default browser as a fallback
                try {
                    val mailtoUri = "mailto:$emailAddress?subject=${
                        java.net.URLEncoder.encode(subject, "UTF-8")
                    }&body=${java.net.URLEncoder.encode(body, "UTF-8")}"
                    desktop.browse(URI(mailtoUri))
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("Error opening mailto link in browser: ${e.message}")
                }
            }
        } else {
            println("Desktop is not supported.")
        }
    }
}
