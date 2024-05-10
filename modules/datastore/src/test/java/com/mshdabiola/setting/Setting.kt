package com.mshdabiola.setting
//
// import com.mshdabiola.model.DummySetting
// import com.mshdabiola.analytics.di.getCommonModule
// import com.russhwolf.settings.ExperimentalSettingsApi
// import com.russhwolf.settings.MapSettings
// import com.russhwolf.settings.coroutines.toFlowSettings
// import kotlinx.coroutines.ExperimentalCoroutinesApi
// import kotlinx.coroutines.test.runTest
// import org.junit.Rule
// import org.junit.Test
// import org.koin.core.component.inject
// import org.koin.dsl.module
// import org.koin.test.KoinTest
// import org.koin.test.KoinTestRule
//
// class Setting : KoinTest {
//
//    @OptIn(ExperimentalSettingsApi::class)
//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        val module = module {
//            single {
//                val settings = MapSettings()
//                settings.toFlowSettings(get())
//
//            }
//
//        }
//        // Your KoinApplication instance here
//        modules(module, commonModule)
//
//    }
//
//    @Test
//    @OptIn(ExperimentalCoroutinesApi::class)
//    fun getDummy() = runTest {
//        val stt by inject<MultiplatformSettings>()
//        stt.setDummy(DummySetting("Adde", "male"))
//    }
// }
