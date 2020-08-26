package com.kotlin.demo.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlin.demo.data.source.local.dao.MovieDao
import com.kotlin.demo.data.source.local.table.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        const val DB_NAME = "MovieDatabase.db"
    }
}