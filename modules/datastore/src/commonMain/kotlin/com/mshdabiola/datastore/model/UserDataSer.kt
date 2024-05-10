/*
 *abiola 2024
 */

package com.mshdabiola.datastore.model

import androidx.datastore.core.okio.OkioSerializer
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

/**
 * Class summarizing user interest data
 */
@Serializable
data class UserDataSer(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val shouldHideOnboarding: Boolean,
    val contrast: Contrast,
)

fun UserData.toSer() =
    UserDataSer(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)

fun UserDataSer.toData() =
    UserData(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)

val json = Json

internal object UserDataJsonSerializer : OkioSerializer<UserDataSer> {

    override val defaultValue: UserDataSer
        get() = UserDataSer(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.LIGHT,
            useDynamicColor = false,
            shouldHideOnboarding = false,
            contrast = Contrast.Normal,
        )

    override suspend fun readFrom(source: BufferedSource): UserDataSer {
        return json.decodeFromString<UserDataSer>(source.readUtf8())
    }

    override suspend fun writeTo(userDataSer: UserDataSer, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserDataSer.serializer(), userDataSer))
        }
    }
}
