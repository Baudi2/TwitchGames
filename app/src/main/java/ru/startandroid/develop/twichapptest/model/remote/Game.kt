package ru.startandroid.develop.twichapptest.model.remote

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey
    val id: String,
    @Embedded
    val logo: ImageUrl?,
    val name: String
)