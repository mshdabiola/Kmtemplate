/*
 *abiola 2024
 */

package com.mshdabiola.datastore.model

import androidx.datastore.core.okio.OkioSerializer
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

/**
 * Class summarizing user interest data
 */
@Serializable
data class UserDataSer(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val shouldHideOnboarding: Boolean,
    val contrast: Contrast,
)
