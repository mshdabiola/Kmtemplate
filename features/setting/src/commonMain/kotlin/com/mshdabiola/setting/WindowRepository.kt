package com.mshdabiola.setting

interface WindowRepository {
    fun openUrl(url: String)
    fun openEmail(emailAddress: String, subject: String, body: String)
}
