package com.mshdabiola.data.repository

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ModelTest {

    @Test
    fun insert() = runTest {
        assertEquals("hello", "mshdabiola")
    }
}
