package ru.startandroid.develop.twichapptest.model.remote

import androidx.room.Entity

@Entity(tableName = "top_games")
data class TopGames(
    val top: List<GameItem>
)