/*
 *abiola 2024
 */

package com.mshdabiola.model

/**
 * Class summarizing user interest data
 */
data class UserData(
    val themeBrand: ThemeBrand = ThemeBrand.DEFAULT,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.DARK,
    val useDynamicColor: Boolean = true,
    val shouldHideOnboarding: Boolean = false,
)
