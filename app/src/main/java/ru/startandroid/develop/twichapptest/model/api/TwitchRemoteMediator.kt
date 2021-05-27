package ru.startandroid.develop.twichapptest.model.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.startandroid.develop.twichapptest.model.data.GameItem
import ru.startandroid.develop.twichapptest.model.data.RemoteKeysEntity
import ru.startandroid.develop.twichapptest.model.db.TwitchDatabase
import java.io.IOException
import javax.inject.Inject

const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class TwitchRemoteMediator @Inject constructor(
    private val api: TwitchApi,
    private val gameDatabase: TwitchDatabase
) : RemoteMediator<Int, GameItem>() {

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
            val apiResponse = api.getTopGames(state.config.pageSize, 0).top

            val endOfPaginationReached = apiResponse.isEmpty()

            gameDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gameDatabase.remoteKeysDao().clearRemoteKeys()
                    gameDatabase.gamesDao().deleteAllGames()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = apiResponse.map {
                    RemoteKeysEntity(
                        gameId = it.game?._id?.toLong(),
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                gameDatabase.remoteKeysDao().insertAll(keys)
                gameDatabase.gamesDao().insertGames(apiResponse)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GameItem>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { game ->
                gameDatabase.remoteKeysDao().remoteKeysGameId(game.game?._id!!.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GameItem>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { game ->
                gameDatabase.remoteKeysDao().remoteKeysGameId(game.game?._id!!.toLong())
            }
    }

    private suspend fun getKeyClosestToCurrentPosition(state: PagingState<Int, GameItem>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.game?._id?.toLong()?.let { gameId ->
                gameDatabase.remoteKeysDao().remoteKeysGameId(gameId)
            }
        }
    }
}

















