package ar.edu.ort.vora.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val  MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(""""
            CREATE TABLE IF NOT EXISTS `meals` (
            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            `name` TEXT NOT NULL,
            `date` TEXT NOT NULL,
            `type` TEXT NOT NULL,
            `kcal` INTEGER NOT NULL,
            `carbs` REAL NOT NULL,
            `fat` REAL NOT NULL,
            `protein` REAL NOT NULL
            )
            """.trimIndent()
            )
    }
    }
