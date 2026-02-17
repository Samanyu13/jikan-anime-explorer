package com.example.jikananimeexplorer.di

import com.example.jikananimeexplorer.data.local.db.AnimeDatabase
import com.example.jikananimeexplorer.data.remote.AnimeApi
import com.example.jikananimeexplorer.data.remote.AnimeRemoteDataSource
import com.example.jikananimeexplorer.data.repository.AnimeRepositoryImpl
import com.example.jikananimeexplorer.domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAnimeRemoteDataSource(api: AnimeApi): AnimeRemoteDataSource {
        return AnimeRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(
        api: AnimeApi,
        database: AnimeDatabase,
        remoteDataSource: AnimeRemoteDataSource
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            api,
            database,
            remoteDataSource
        )
    }
}