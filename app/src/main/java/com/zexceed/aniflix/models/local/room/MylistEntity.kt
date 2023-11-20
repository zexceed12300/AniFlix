package com.zexceed.aniflix.models.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Mylist")
data class MylistEntity(
    @ColumnInfo(name = "animeId")
    @PrimaryKey
    val animeId: String,

    @ColumnInfo(name = "thumb")
    val thumb: String,

    @ColumnInfo(name = "title")
    val title: String
)
