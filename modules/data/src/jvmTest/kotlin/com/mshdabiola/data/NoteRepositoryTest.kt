/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.data

import com.mshdabiola.data.doubles.TestNoteDao
import com.mshdabiola.data.repository.RealModelRepository
import com.mshdabiola.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteRepositoryTest {

    private lateinit var noteDao: TestNoteDao
    private lateinit var repository: RealModelRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        noteDao = TestNoteDao()
        repository = RealModelRepository(noteDao, testDispatcher)
    }

    @Test
    fun `upsert new note returns valid id and adds note`() = runTest(testDispatcher) {
        val newNote = Note(title = "New Note", content = "Content")
        val id = repository.upsert(newNote)

        assertTrue(id > 0L)
        val insertedNote = repository.getOne(id).first()
        assertNotNull(insertedNote)
        assertEquals("New Note", insertedNote?.title)
    }

    @Test
    fun `upsert existing note updates it`() = runTest(testDispatcher) {
        val initialNote = Note(title = "Initial Note", content = "Initial Content")
        val id = repository.upsert(initialNote)

        val updatedNote = Note(id = id, title = "Updated Note", content = "Updated Content")
        val updatedId = repository.upsert(updatedNote)

        assertEquals(id, updatedId)
        val fetchedNote = repository.getOne(id).first()
        assertNotNull(fetchedNote)
        assertEquals("Updated Note", fetchedNote?.title)
        assertEquals("Updated Content", fetchedNote?.content)
    }

    @Test
    fun `getAll returns empty list initially`() = runTest(testDispatcher) {
        val notes = repository.getAll().first()
        assertTrue(notes.isEmpty())
    }

    @Test
    fun `getAll returns inserted notes`() = runTest(testDispatcher) {
        val note1 = Note(title = "Note 1", content = "Content 1")
        val note2 = Note(title = "Note 2", content = "Content 2")
        repository.upsert(note1)
        repository.upsert(note2)

        val notes = repository.getAll().first()
        assertEquals(2, notes.size)
    }

    @Test
    fun `getOne returns null for non-existent id`() = runTest(testDispatcher) {
        val note = repository.getOne(999L).first()
        assertNull(note)
    }

    @Test
    fun `getOne returns correct note`() = runTest(testDispatcher) {
        val note = Note(title = "Test Note", content = "Test Content")
        val id = repository.upsert(note)

        val fetchedNote = repository.getOne(id).first()
        assertNotNull(fetchedNote)
        assertEquals(id, fetchedNote?.id)
        assertEquals("Test Note", fetchedNote?.title)
    }

    @Test
    fun `delete removes note`() = runTest(testDispatcher) {
        val note = Note(title = "To Delete", content = "Delete Content")
        val id = repository.upsert(note)

        var fetchedNote = repository.getOne(id).first()
        assertNotNull(fetchedNote) // Ensure it was added

        repository.delete(id)
        fetchedNote = repository.getOne(id).first()
        assertNull(fetchedNote) // Ensure it was deleted
    }
}
