package ar.edu.ort.vora.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * v1 -> v2: crea todas las tablas de la versión 2 (meals, workouts, exercises).
 * El esquema tiene que coincidir exactamente con las entidades, si no Room
 * lanza "Migration didn't properly handle" al abrir la base.
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
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

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `workouts` (
                `id` TEXT NOT NULL,
                `name` TEXT NOT NULL,
                `level` TEXT NOT NULL,
                `durationMinutes` INTEGER NOT NULL,
                `exerciseCount` INTEGER NOT NULL,
                `description` TEXT,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `exercises` (
                `id` TEXT NOT NULL,
                `workoutId` TEXT NOT NULL,
                `name` TEXT NOT NULL,
                `muscleGroup` TEXT NOT NULL,
                `sets` INTEGER NOT NULL,
                `reps` INTEGER NOT NULL,
                `restSeconds` INTEGER NOT NULL,
                `instructions` TEXT NOT NULL,
                `tip` TEXT NOT NULL,
                PRIMARY KEY(`id`),
                FOREIGN KEY(`workoutId`) REFERENCES `workouts`(`id`)
                    ON UPDATE NO ACTION ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_exercises_workoutId` " +
                "ON `exercises` (`workoutId`)"
        )
    }
}
