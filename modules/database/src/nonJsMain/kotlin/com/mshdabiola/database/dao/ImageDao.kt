/*
 *abiola 2024
 */

package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.database.model.ImageEntity

@Dao
interface ImageDao {
    @Upsert
    suspend fun insertAll(users: List<ImageEntity>)

//    @Query("SELECT * FROM image_table")
//    suspend fun pagingSource(): PagingSource<Int, ImageEntity>

    @Query("DELETE FROM image_table")
    suspend fun clearAll()
}
