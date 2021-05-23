package ru.startandroid.develop.twichapptest.model.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.startandroid.develop.twichapptest.model.remote.GameItem

@Dao
interface TwitchDao {

    @Query("SELECT * FROM top_games")
    fun getAllGames(): PagingSource<Int, TwitchDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<TwitchDataEntity>)

    @Query("DELETE FROM top_games")
    suspend fun deleteAllGames()
}