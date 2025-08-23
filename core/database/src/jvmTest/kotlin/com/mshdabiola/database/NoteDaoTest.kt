/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Tests for [NoteDao].
 * This uses an in-memory database for testing.
 *
 * For pure JVM tests without Android framework dependencies (like Context),
 * you might need to adjust the database builder or use a runner like Robolectric
 * if your Room setup (or other dependencies) implicitly requires Android APIs.
 *
 * If `ApplicationProvider.getApplicationContext()` is an issue for pure JVM,
 * you can try `Room.inMemoryDatabaseBuilder(null, AppDatabase::class.java)` if it works,
 * or ensure your testing environment can provide a minimal context (e.g., via Robolectric).
 *
 * For simplicity and common Android testing patterns, RobolectricTestRunner is often used.
 * If you want a pure JVM test without Robolectric, you'd need to ensure your
 * Room.inMemoryDatabaseBuilder doesn't require a Context, or use a different test setup.
 */

class NoteDaoTest {

    private lateinit var database: KmtDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun createDb() {
        database =
            Room
                .inMemoryDatabaseBuilder<KmtDatabase>()
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        noteDao = database.getNoteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNote() = runTest {
        val note = NoteEntity(null, title = "Test Title", content = "Test Content")
        val generatedId = noteDao.upsert(note) // upsert returns the new rowId

        val retrievedNoteFlow = noteDao.getOne(generatedId)
        val retrievedNote = retrievedNoteFlow.first() // Collect the first emitted value

        assertNotNull(retrievedNote)
        assertEquals(generatedId, retrievedNote.id)
        assertEquals("Test Title", retrievedNote.title)
        assertEquals("Test Content", retrievedNote.content)
    }

    @Test
    fun getAllNotes_whenEmpty() = runTest {
        val allNotes = noteDao.getAll().first()
        assertTrue(allNotes.isEmpty(), "Database should be empty initially")
    }

    @Test
    fun getAllNotes_afterInsertingMultiple() = runTest {
        val note1 = NoteEntity(null, title = "Note 1", content = "Content 1")
        val note2 = NoteEntity(null, title = "Note 2", content = "Content 2")
        noteDao.upsert(note1)
        noteDao.upsert(note2)

        val allNotes = noteDao.getAll().first()
        assertEquals(2, allNotes.size)
    }

    @Test
    fun getOneNote_nonExistent() = runTest {
        val note = noteDao.getOne(999L).first() // ID that doesn't exist
        assertNull(note, "Should return null for a non-existent ID")
    }

    @Test
    fun upsert_insertsNewNote() = runTest {
        val newNote = NoteEntity(null, title = "New", content = "Fresh")
        val id = noteDao.upsert(newNote)
        assertTrue(id > 0, "Upsert should return a positive ID for new inserts")

        val retrieved = noteDao.getOne(id).first()
        assertNotNull(retrieved)
        assertEquals("New", retrieved.title)
    }

    @Test
    fun upsert_updatesExistingNote() = runTest {
        val initialNote = NoteEntity(null, title = "Original", content = "Old Content")
        val id = noteDao.upsert(initialNote) // Insert first

        val updatedNote = NoteEntity(id = id, title = "Updated", content = "New Content")
        noteDao.upsert(updatedNote) // Update

        val retrieved = noteDao.getOne(id).first()
        assertNotNull(retrieved)
        assertEquals(id, retrieved.id)
        assertEquals("Updated", retrieved.title)
        assertEquals("New Content", retrieved.content)
    }

    @Test
    fun deleteNote() = runTest {
        val note = NoteEntity(null, title = "To Delete", content = "Delete Me")
        val id = noteDao.upsert(note)

        assertNotNull(noteDao.getOne(id).first(), "Note should exist before delete")

        noteDao.delete(id)
        assertNull(noteDao.getOne(id).first(), "Note should be null after delete")
    }

    @Test
    fun insertAllAndClearAll() = runTest {
        val notes = listOf(
            NoteEntity(null, title = "Bulk 1", content = "C1"),
            NoteEntity(null, title = "Bulk 2", content = "C2"),
        )
        noteDao.insertAll(notes)

        var allNotes = noteDao.getAll().first()
        assertEquals(2, allNotes.size)

        noteDao.clearAll()
        allNotes = noteDao.getAll().first()
        assertTrue(allNotes.isEmpty(), "All notes should be cleared")
    }
}
