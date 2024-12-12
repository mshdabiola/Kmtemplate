package database

import app.cash.turbine.test
import com.mshdabiola.database.dao.NoteDaoN
import com.mshdabiola.database.model.NoteEntityN
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ModelTest : AbstractTest() {

    @Test
    override fun insert() = runTest {
        val modelDao by inject<NoteDaoN>()

        modelDao.upsert(
            NoteEntityN(
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

    @Test
    override fun delete() {
    }

    @Test
    override fun getOne() {
    }

    @Test
    override fun getAll() {
    }
}
