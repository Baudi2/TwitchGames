package ru.startandroid.develop.twichapptest.model

import androidx.lifecycle.LiveData
import androidx.paging.*
import ru.startandroid.develop.twichapptest.model.api.TwitchApi
import ru.startandroid.develop.twichapptest.model.api.TwitchRemoteMediator
import ru.startandroid.develop.twichapptest.model.data.GameItem
import ru.startandroid.develop.twichapptest.model.db.TwitchDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val twitchApi: TwitchApi,
    private val database: TwitchDatabase
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 15
    }

    fun getTopGames(): LiveData<PagingData<GameItem>> {
        val pagingSourceFactory = { database.gamesDao().getAllGames() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 100,
                enablePlaceholders = false
            ),
            remoteMediator = TwitchRemoteMediator(
                twitchApi,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }
}