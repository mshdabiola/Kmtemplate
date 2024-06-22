/*
 *abiola 2024
 */

package com.mshdabiola.data.repository

import com.mshdabiola.data.testdoubles.TestNoteDao
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DefaultNoteRepositoryTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var noteDao: TestNoteDao
    private lateinit var defaultNoteRepository: RealModelRepository

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        noteDao = TestNoteDao()

        defaultNoteRepository = RealModelRepository(noteDao, UnconfinedTestDispatcher())
    }

    @Test
    fun upsert() {
    }

    @Test
    fun getAll() {
    }

    @Test
    fun getOne() {
    }

    @Test
    fun delete() {
    }
}
