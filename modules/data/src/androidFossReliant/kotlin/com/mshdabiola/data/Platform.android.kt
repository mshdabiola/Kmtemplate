package com.mshdabiola.data

import android.os.Build

actual fun getPlatform(): Platform {
    val buildType = "fossReliant"
    val sdk = Build.VERSION.SDK_INT

    return Platform.Android.FossReliant(buildType, sdk)
}
