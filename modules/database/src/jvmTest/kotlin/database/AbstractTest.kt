package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.database.SkeletonDatabase
import com.mshdabiola.database.di.daoModules
import com.mshdabiola.database.di.getRoomDatabase
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {
    @get:Rule
    val koinTestRule =
        KoinTestRule.create {
            val module =
                module {
                    single {
                        val db =
                            Room
                                .inMemoryDatabaseBuilder<SkeletonDatabase>()
                                .setDriver(BundledSQLiteDriver())
                        getRoomDatabase(db)
                    }
                }
            // Your KoinApplication instance here
            modules(module, daoModules)
        }

    abstract fun insert()

    abstract fun delete()

    abstract fun getOne()

    abstract fun getAll()
}
