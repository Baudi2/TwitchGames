package ru.startandroid.develop.twichapptest.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.startandroid.develop.twichapptest.model.data.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_key WHERE gameId = :gameId")
    suspend fun remoteKeysGameId(gameId: Long): RemoteKeysEntity?

    @Query("DELETE FROM REMOTE_KEY")
    suspend fun clearRemoteKeys()
}