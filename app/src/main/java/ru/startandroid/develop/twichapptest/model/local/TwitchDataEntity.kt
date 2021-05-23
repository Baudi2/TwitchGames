package ru.startandroid.develop.twichapptest.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_games")
data class TwitchDataEntity(
    @PrimaryKey
    val id: Long,
    val viewers: String,
    val channels: String,
    val logo: String,
    val name: String,
)