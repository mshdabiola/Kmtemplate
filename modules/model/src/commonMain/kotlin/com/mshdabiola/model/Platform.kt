package com.mshdabiola.model

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
