package com.zexceed.aniflix.models.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MylistEntity::class, HistoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AniflixDatabase : RoomDatabase() {
    abstract fun myListDao(): MylistDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AniflixDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AniflixDatabase {
            if (INSTANCE == null) {
                synchronized(AniflixDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AniflixDatabase::class.java, "mylist_database"
                    ).build()
                }
            }

            return INSTANCE as AniflixDatabase
        }
    }
}