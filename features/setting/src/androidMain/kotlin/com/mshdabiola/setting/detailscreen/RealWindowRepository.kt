package com.mshdabiola.setting.detailscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.mshdabiola.setting.WindowRepository

class RealWindowRepository(val context: Context): WindowRepository {
    override fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }

    override fun openEmail(emailAddress: String, subject: String, body: String) {
        val mailto = "mailto:${Uri.encode(emailAddress)}" +
            "?subject=${Uri.encode(subject)}" +
            "&body=${Uri.encode(body)}"

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = mailto.toUri()
        }

        // Check if there's an app to handle this intent
        if (emailIntent.resolveActivity(context.packageManager) != null) {
            ContextCompat.startActivity(context, emailIntent, null)
        } else {
            // Optionally handle the case where no email app is installed
            // e.g., show a Toast or log a message
            println("No email app found to handle the intent.")
        }
    }
}
