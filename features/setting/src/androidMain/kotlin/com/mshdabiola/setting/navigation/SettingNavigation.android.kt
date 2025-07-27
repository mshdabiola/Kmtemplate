package com.mshdabiola.setting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mshdabiola.setting.WindowRepository
import com.mshdabiola.setting.detailscreen.RealWindowRepository

@Composable
actual fun getWindowRepository(): WindowRepository {
    val context = LocalContext.current
    return RealWindowRepository(context)
}
