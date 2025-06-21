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
package com.mshdabiola.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.mshdabiola.designsystem.R

val provider =
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.modules_designsystem_com_google_android_gms_fonts_certs,
    )

val bodyFontFamily =
    FontFamily(
        Font(
            googleFont = GoogleFont("Glegoo"),
            fontProvider = provider,
        ),
    )

val displayFontFamily =
    FontFamily(
        Font(
            googleFont = GoogleFont("Montserrat"),
            fontProvider = provider,
        ),
    )

// Default Material 3 typography values
val baseline = androidx.compose.material3.Typography()

internal actual val SkTypography: Typography
    get() =
        androidx.compose.material3.Typography(
            displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
            displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
            displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
            headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
            headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
            headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
            titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
            titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
            titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
            bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
            bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
            bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
            labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
            labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
            labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
        )
