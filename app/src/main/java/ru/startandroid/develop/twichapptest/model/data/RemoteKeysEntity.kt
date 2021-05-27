package ru.startandroid.develop.twichapptest.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKeysEntity(
    @PrimaryKey
    val gameId: Long?,
    val prevKey: Int?,
    val nextKey: Int?
)
