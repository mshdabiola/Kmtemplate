package com.mshdabiola.data

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override val buildType: String
        get() = "Web"
}

actual fun getPlatform(): Platform = WasmPlatform()
