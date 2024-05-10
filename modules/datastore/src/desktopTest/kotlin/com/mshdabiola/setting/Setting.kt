package com.mshdabiola.setting

import app.cash.turbine.test
import com.mshdabiola.datastore.MultiplatformSettings
import com.mshdabiola.model.ThemeBrand
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.component.inject
import kotlin.test.assertEquals

class Setting : AbstractTest() {

    @Test
    fun getDummy() = runTest {
        val stt by inject<MultiplatformSettings>()
        stt.setThemeBrand(ThemeBrand.GREEN)

        stt.userData
            .test {
                val uesr = awaitItem()
                assertEquals(uesr.themeBrand, ThemeBrand.GREEN)
                awaitComplete()
            }
    }
}
