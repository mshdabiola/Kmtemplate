package com.mshdabiola.setting.detailscreen

import com.mshdabiola.setting.WindowRepository
import java.awt.Desktop
import java.net.URI

class RealWindowRepository: WindowRepository {
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
