package ru.startandroid.develop.twichapptest.model.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey
    val _id: Int,
    @Embedded
    val logo: ImageUrl?,
    val name: String
)