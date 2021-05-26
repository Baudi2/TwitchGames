package ru.startandroid.develop.twichapptest.model.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.startandroid.develop.twichapptest.model.relations.GameItemWithGame
import ru.startandroid.develop.twichapptest.model.remote.GameItem

@Dao
interface TwitchDao {

    @Query("SELECT * FROM top_games")
    fun getAllGames(): PagingSource<Int, GameItemWithGame>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameItem>)

    @Query("DELETE FROM top_games")
    suspend fun deleteAllGames()
}