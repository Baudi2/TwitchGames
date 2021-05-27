package ru.startandroid.develop.twichapptest.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.startandroid.develop.twichapptest.model.Converters
import ru.startandroid.develop.twichapptest.model.remote.Game
import ru.startandroid.develop.twichapptest.model.remote.GameItem

@Database(
    entities = [GameItem::class, Game::class, RemoteKeysEntity::class],
    version = 14
)
@TypeConverters(Converters::class)
abstract class TwitchDatabase : RoomDatabase() {

    abstract fun gamesDao(): TwitchDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}