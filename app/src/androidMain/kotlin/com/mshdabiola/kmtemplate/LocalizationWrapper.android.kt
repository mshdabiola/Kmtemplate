package com.mshdabiola.kmtemplate

import java.util.Locale


actual fun changeLanguage(language: String) {
    Locale.setDefault(Locale(language))
}
