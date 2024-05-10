package com.mshdabiola.data.repository

import com.mshdabiola.model.DummySetting

interface ISettingRepository {
    suspend fun setDummy(dummy: DummySetting)
}
