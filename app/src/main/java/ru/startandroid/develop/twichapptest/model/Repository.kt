package ru.startandroid.develop.twichapptest.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import ru.startandroid.develop.twichapptest.model.remote.TwitchApi
import ru.startandroid.develop.twichapptest.model.remote.TwitchPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val twitchApi: TwitchApi) {

    fun getTopGames() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TwitchPagingSource(twitchApi) }
        ).liveData
}