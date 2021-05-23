package ru.startandroid.develop.twichapptest.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.startandroid.develop.twichapptest.model.local.TwitchDatabase
import ru.startandroid.develop.twichapptest.model.remote.TwitchApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(TwitchApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTwitchApi(retrofit: Retrofit): TwitchApi =
        retrofit.create(TwitchApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TwitchDatabase =
        Room.databaseBuilder(app, TwitchDatabase::class.java, "games_database")
            .fallbackToDestructiveMigration()
            .build()
}