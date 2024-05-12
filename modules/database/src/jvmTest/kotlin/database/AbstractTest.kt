package database

import androidx.room.Room
import com.mshdabiola.database.SkeletonDatabase
import com.mshdabiola.database.di.daoModules
import com.mshdabiola.database.di.getRoomDatabase
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val module = module {
            single {
                val db = Room.inMemoryDatabaseBuilder<SkeletonDatabase>()
                getRoomDatabase(db)
            }
        }
        // Your KoinApplication instance here
        modules(module, daoModules)
    }

    @Test
    abstract fun insert()

    @Test
    abstract fun delete()

    @Test
    abstract fun getOne()

    @Test
    abstract fun getAll()
}
