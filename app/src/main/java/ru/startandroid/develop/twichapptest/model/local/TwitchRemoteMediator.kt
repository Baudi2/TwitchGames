package ru.startandroid.develop.twichapptest.model.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.startandroid.develop.twichapptest.model.remote.GameItem
import ru.startandroid.develop.twichapptest.model.remote.TwitchApi
import java.io.IOException
import javax.inject.Inject

const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class TwitchRemoteMediator @Inject constructor(
    private val api: TwitchApi,
    private val gameDatabase: TwitchDatabase
) : RemoteMediator<Int, GameItem>() {
    private lateinit var entityGames: ArrayList<TwitchDataEntity>

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = api.getTopGames(state.config.pageSize, page)

            val games = apiResponse.top
            val endOfPaginationReached = games.isEmpty()

            gameDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gameDatabase.remoteKeysDao().clearRemoteKeys()
                    gameDatabase.gamesDao().deleteAllGames()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = games.map {
                    RemoteKeysEntity(
                        gameId = it.game.id.toLong(),
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                games.forEach {
                    val temp = TwitchDataEntity(
                        it.game.id.toLong(),
                        it.viewers.toString(),
                        it.channels.toString(),
                        it.game.logo.large,
                        it.game.name
                    )
                    entityGames.add(temp)
                }

                gameDatabase.remoteKeysDao().insertAll(keys)
                gameDatabase.gamesDao().insertGames(entityGames)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GameItem>): RemoteKeysEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { game ->
                gameDatabase.remoteKeysDao().remoteKeysGameId(game.game.id.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GameItem>): RemoteKeysEntity? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { game ->
                gameDatabase.remoteKeysDao().remoteKeysGameId(game.game.id.toLong())
            }
    }

    private suspend fun getKeyClosestToCurrentPosition(state: PagingState<Int, GameItem>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.game?.id?.toLong()?.let { gameId ->
                gameDatabase.remoteKeysDao().remoteKeysGameId(gameId)
            }
        }
    }
}

















