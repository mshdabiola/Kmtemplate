/*
 *abiola 2024
 */

package com.mshdabiola.datastore.model

import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import kotlinx.serialization.Serializable

/**
 * Class summarizing user interest data
 */
@Serializable
data class UserDataSer(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val shouldHideOnboarding: Boolean,
)
