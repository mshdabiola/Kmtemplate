package com.mshdabiola.skeletonapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.skeletonapp.screen.detail.DetailScreenn
import com.mshdabiola.skeletonapp.screen.main.MainScreenNav
import com.mshdabiola.skeletonapp.screen.setting.SettingScreenNav

@Composable
fun SkeletonAppNavHost(appState: SkAppState) {
    Children(
        stack = appState.navController.stack,
        modifier = Modifier,
        animation = stackAnimation(fade() + slide()),
    ) {
        when (it.instance) {
            is IRootComponent.RootScreen.MainRootScreen -> {
                MainScreenNav(
                    navigateToDetail = appState.navController::navigateToDetail,
                    navigateToSetting = appState.navController::navigateToSetting,
                )
            }

            is IRootComponent.RootScreen.DetailRootScreen -> {
                DetailScreenn(appState.windowSizeClass) {
                    appState.navController.back()
                }
            }

            is IRootComponent.RootScreen.SettingRootScreen -> {
                SettingScreenNav {
                    appState.navController.back()
                }
            }
        }
    }
}
