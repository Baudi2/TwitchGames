package ru.startandroid.develop.twichapptest.model.remote

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @PrimaryKey
    val id: String,
    val logo: ImageUrl?,
    val name: String
)
