package ru.startandroid.develop.twichapptest.model.remote

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_item")
data class GameItem(
    val game: Game?,
    @PrimaryKey val viewers: Int,
    val channels: Int
)