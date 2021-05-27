package ru.startandroid.develop.twichapptest.model.remote

import androidx.paging.PagingSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val STARTING_PAGE_INDEX = 1

class TwitchPagingSource @Inject constructor(
    private val twitchApi: TwitchApi
) : PagingSource<Int, GameItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItem> {
        val position = params.key ?: STARTING_PAGE_INDEX

        twitchApi.getTopGames(params.loadSize, 10).top

        return try {
            val response = twitchApi.getTopGames(params.loadSize, 1).top

            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}