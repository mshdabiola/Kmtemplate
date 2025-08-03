package com.mshdabiola.data

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val buildType: String
        get() = "fossReliant"
}

actual fun getPlatform(): Platform = AndroidPlatform()
