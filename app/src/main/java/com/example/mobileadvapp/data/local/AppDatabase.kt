package com.example.mobileadvapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Student::class, Course::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Migrasi 1 -> 2: menambah tabel courses tanpa menghapus data student
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `courses` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `course_code` TEXT NOT NULL,
                        `course_name` TEXT NOT NULL,
                        `credits` INTEGER NOT NULL,
                        `lecturer_name` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}