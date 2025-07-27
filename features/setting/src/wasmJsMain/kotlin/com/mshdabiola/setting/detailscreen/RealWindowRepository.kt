package com.mshdabiola.setting.detailscreen

import com.mshdabiola.setting.WindowRepository
import kotlinx.browser.window

class RealWindowRepository: WindowRepository {
    override fun openUrl(url: String) {
        window.open(url, "_blank")
    }

    override fun openEmail(emailAddress: String, subject: String, body: String) {
        // Simple mailto link for Wasm/JS.
        // Encoding subject and body is important for special characters.
        val encodedSubject = kotlin.js.js("encodeURIComponent(subject)") as String
        val encodedBody = kotlin.js.js("encodeURIComponent(body)") as String
        window.open("mailto:$emailAddress?subject=$encodedSubject&body=$encodedBody", "_self")

    }
}
