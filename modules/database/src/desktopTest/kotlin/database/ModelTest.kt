package database

import app.cash.turbine.test
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ModelTest : AbstractTest() {

    override fun insert() = runTest {
        val modelDao by inject<NoteDao>()

        modelDao.upsert(
            NoteEntity(
                id = null,
                title = "abiola",
                content = "Adisl",
            ),
        )
        modelDao.getAll()
            .test {
                val list = awaitItem()
                print(list)
                assertEquals(1, list.size)
                this.cancelAndIgnoreRemainingEvents()
            }
    }

    override fun delete() {
    }

    override fun getOne() {
    }

    override fun getAll() {
    }
}
