package com.mshdabiola.data

class JVMPlatform : Platform {
    override val name: String
        get() {
            val operSys = System.getProperty("os.name").lowercase()
            val os =
                if (operSys.contains("win")) {
                    "Windows"
                } else if (operSys.contains("nix") ||
                    operSys.contains("nux") ||
                    operSys.contains("aix")
                ) {
                    "Linux"
                } else if (operSys.contains("mac")) {
                    "MacOS"
                } else {
                    //  Logger.e("PlatformUtil.jvm") { "Unknown platform: $operSys" }
                    "Linux"
                }
            val javaVersion = System.getProperty("java.version")

            return "$os $javaVersion"
        }
}

actual fun getPlatform(): Platform = JVMPlatform()
