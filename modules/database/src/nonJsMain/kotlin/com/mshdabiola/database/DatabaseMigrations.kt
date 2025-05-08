/*
 *abiola 2024
 */

package com.mshdabiola.database

import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

/**
 * Automatic schema migrations sometimes require extra instructions to perform the migration, for
 * example, when a column is renamed. These extra instructions are placed here by creating a class
 * using the following naming convention `SchemaXtoY` where X is the schema version you're migrating
 * from and Y is the schema version you're migrating to. The class should implement
 * `AutoMigrationSpec`.
 */
object DatabaseMigrations {
    @RenameColumn(
        tableName = "topics",
        fromColumnName = "description",
        toColumnName = "shortDescription",
    )
    class Schema2to3 : AutoMigrationSpec

    @DeleteColumn(
        tableName = "news_resources",
        columnName = "episode_id",
    )
    @DeleteTable.Entries(
        DeleteTable(
            tableName = "episodes_authors",
        ),
        DeleteTable(
            tableName = "episodes",
        ),
    )
    class Schema10to11 : AutoMigrationSpec

    @DeleteTable.Entries(
        DeleteTable(
            tableName = "news_resources_authors",
        ),
        DeleteTable(
            tableName = "authors",
        ),
    )
    class Schema11to12 : AutoMigrationSpec
}
