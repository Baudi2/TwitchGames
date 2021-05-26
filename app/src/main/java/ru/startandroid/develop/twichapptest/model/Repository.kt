package ru.startandroid.develop.twichapptest.model

import androidx.lifecycle.LiveData
import androidx.paging.*
import ru.startandroid.develop.twichapptest.model.local.TwitchDatabase
import ru.startandroid.develop.twichapptest.model.local.TwitchRemoteMediator
import ru.startandroid.develop.twichapptest.model.remote.GameItem
import ru.startandroid.develop.twichapptest.model.remote.TwitchApi
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

    fun getTopGames() : LiveData<PagingData<GameItem>> {
        val pagingSourceFactory = {database.gamesDao().getAllGames()}

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

//    fun getTopGames() =
//        Pager(
//            config = PagingConfig(
//                pageSize = 5,
//                maxSize = 100,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { TwitchPagingSource(twitchApi) }
//        ).liveData
}