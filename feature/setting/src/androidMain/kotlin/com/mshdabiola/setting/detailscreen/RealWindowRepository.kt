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

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.mshdabiola.setting.WindowRepository

class RealWindowRepository(val context: Context) : WindowRepository {
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
