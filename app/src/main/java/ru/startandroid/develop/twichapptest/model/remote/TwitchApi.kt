package ru.startandroid.develop.twichapptest.model.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitchApi {
    companion object {
        const val BASE_URL = "https://api.twitch.tv/kraken/"
        const val CLIENT_ID = "sd4grh0omdj9a31exnpikhrmsu3v46"
    }

    @Headers("Accept: application/vnd.twitchtv.v5+json", "Client-ID: $CLIENT_ID")
    @GET("games/top")
    suspend fun getTopGames(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): TopGames
}