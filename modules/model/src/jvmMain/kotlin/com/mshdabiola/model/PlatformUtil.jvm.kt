package com.mshdabiola.model


actual val currentPlatform: Platform
    get() {
        val operSys = System.getProperty("os.name").lowercase()
        return if (operSys.contains("win")) {
            Platform.Windows
        } else if (operSys.contains("nix") || operSys.contains("nux") ||
            operSys.contains("aix")
        ) {
            Platform.Linux
        } else if (operSys.contains("mac")) {
            Platform.MacOS
        } else {
          //  Logger.e("PlatformUtil.jvm") { "Unknown platform: $operSys" }
            Platform.Linux
        }
    }

//actual val currentPlatform: Platform = Platform.Android
