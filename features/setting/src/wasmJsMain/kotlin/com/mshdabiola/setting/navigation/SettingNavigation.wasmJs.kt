package com.mshdabiola.setting.navigation

import androidx.compose.runtime.Composable
import com.mshdabiola.setting.WindowRepository
import com.mshdabiola.setting.detailscreen.RealWindowRepository

@Composable
actual fun getWindowRepository(): WindowRepository {
    return RealWindowRepository()
}
