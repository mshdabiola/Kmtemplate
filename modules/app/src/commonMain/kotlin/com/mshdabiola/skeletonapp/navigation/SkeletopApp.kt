package com.mshdabiola.skeletonapp.navigation

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.DefaultComponentContext
import com.mshdabiola.designsystem.theme.SkTheme
import com.mshdabiola.navigation.RootComponent

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SkeletonApp(context: DefaultComponentContext, isDarkMode: Boolean) {
    val rootComp = remember { RootComponent(context) }
    val windowSizeClass = calculateWindowSizeClass()

    SkTheme {
//        SkeletonAppNavHost(
//            iRootComponent = rootComp,
//            modifier = Modifier.fillMaxSize(),
//            windowSizeClass = windowSizeClass
//        )
    }
}
