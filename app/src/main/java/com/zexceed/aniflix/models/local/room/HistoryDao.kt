package com.zexceed.aniflix.models.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History")
    fun getAllHistory(): LiveData<List<HistoryEntity>>

    @Query("SELECT * FROM History WHERE animeId = :animeId")
    fun getHistoryById(animeId: String): LiveData<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Query("DELETE FROM History WHERE animeId = :animeId")
    suspend fun deleteHistoryById(animeId: String)

    @Query("DELETE FROM History")
    suspend fun deleteAllHistory()
}