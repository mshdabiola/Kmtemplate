package com.mshdabiola.data

import android.os.Build

actual fun getPlatform(): Platform {
    val buildType = "googlePlay"
    val sdk = Build.VERSION.SDK_INT

    return Platform.Android.GooglePlay(buildType, sdk)
}
