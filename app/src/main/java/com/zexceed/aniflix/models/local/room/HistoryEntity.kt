package com.zexceed.aniflix.models.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class HistoryEntity(
    @ColumnInfo(name = "animeId")
    @PrimaryKey
    val animeId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "thumb")
    val thumb: String,

    @ColumnInfo(name = "episode")
    val episode: Int,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)
