package ru.startandroid.develop.twichapptest.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.startandroid.develop.twichapptest.model.data.Game
import ru.startandroid.develop.twichapptest.model.data.GameItem
import ru.startandroid.develop.twichapptest.model.data.RemoteKeysEntity

@Database(
    entities = [GameItem::class, Game::class, RemoteKeysEntity::class],
    version = 17
)
@TypeConverters(Converters::class)
abstract class TwitchDatabase : RoomDatabase() {

    abstract fun gamesDao(): TwitchDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}