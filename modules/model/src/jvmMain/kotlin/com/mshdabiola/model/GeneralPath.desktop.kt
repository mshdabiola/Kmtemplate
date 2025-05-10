package com.mshdabiola.model

actual val generalPath: String
    get() = System.getProperty("java.io.tmpdir") + "/hydraulics" // "${System.getProperty("user.home")}/AppData/Local/hydraulicapp"
