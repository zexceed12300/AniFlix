package com.zexceed.aniflix.models.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MylistDao {
    @Query("SELECT * FROM Mylist")
    fun getMyList(): LiveData<List<MylistEntity>>

    @Query("SELECT * FROM Mylist WHERE animeId = :animeId")
    fun getMyListById(animeId: String): LiveData<MylistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeMyList(anime: MylistEntity)

    @Query("DELETE FROM Mylist WHERE animeId = :animeId")
    suspend fun deleteMyListById(animeId: String)
}