/*
 *abiola 2022
 */

package com.mshdabiola.testing.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.dsl.bind
import org.koin.dsl.module

val testDispatcherModule = module {
    single { UnconfinedTestDispatcher() } bind CoroutineDispatcher::class
}