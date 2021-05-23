package ru.startandroid.develop.twichapptest.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TwitchDataEntity::class, RemoteKeysEntity::class], version = 5)
abstract class TwitchDatabase : RoomDatabase() {

    abstract fun gamesDao(): TwitchDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}