package ru.startandroid.develop.twichapptest.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.startandroid.develop.twichapptest.model.Converters
import ru.startandroid.develop.twichapptest.model.remote.Game
import ru.startandroid.develop.twichapptest.model.remote.GameItem
import ru.startandroid.develop.twichapptest.model.remote.ImageUrl
import ru.startandroid.develop.twichapptest.model.remote.TopGames

@Database(
    entities = [TopGames::class, GameItem::class, Game::class, RemoteKeysEntity::class],
    version = 11
)
@TypeConverters(Converters::class)
abstract class TwitchDatabase : RoomDatabase() {

    abstract fun gamesDao(): TwitchDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}