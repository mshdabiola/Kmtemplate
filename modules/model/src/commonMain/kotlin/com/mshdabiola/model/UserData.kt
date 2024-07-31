/*
 *abiola 2024
 */

package com.mshdabiola.model

import kotlinx.serialization.Serializable

/**
 * Class summarizing user interest data
 */
@Serializable
data class UserData(
    val themeBrand: ThemeBrand = ThemeBrand.DEFAULT,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.DARK,
    val useDynamicColor: Boolean = true,
    val shouldHideOnboarding: Boolean = false,
)
