package ru.startandroid.develop.twichapptest.model.remote

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_games")
data class GameItem(
    @Embedded
    val game: Game?,
    @PrimaryKey(autoGenerate = true)
    val viewers: Int,
    val channels: Int
)