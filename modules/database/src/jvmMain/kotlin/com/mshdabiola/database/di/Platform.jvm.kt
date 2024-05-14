package com.mshdabiola.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SkeletonDatabase
import com.mshdabiola.model.generalPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Locale

actual val databaseModule: Module
    get() = module {
        single {
            getRoomDatabase(getDatabaseBuilder())
        }
        includes(daoModules)
    }

fun getDatabaseBuilder(): RoomDatabase.Builder<SkeletonDatabase> {
   val path= File("${System.getProperty("user.home")}/AppData/Local/hydraulic")
    if (path.exists().not()){
        path.mkdirs()
    }
    val file=File(path,"error.txt")


    return  try {
        val dbFile =
            File(
                generalPath,
                Constant.databaseName,
            )
        //File(System.getProperty("java.io.tmpdir"), Constant.databaseName)

        val driver=BundledSQLiteDriver()
         Room.databaseBuilder<SkeletonDatabase>(
            name = dbFile.absolutePath,
        )

            .setDriver(driver)
    }catch (e:Exception){
//        file.bufferedWriter()
//            .write("Catch")
        //file.writeText(e.stackTraceToString())
        e.printStackTrace(PrintWriter(file.bufferedWriter()))
//        file.close()
        throw e
    }


}
