package com.example.jikananimeexplorer.di

import com.example.jikananimeexplorer.data.local.dao.AnimeDao
import com.example.jikananimeexplorer.data.local.db.AnimeDatabase
import com.example.jikananimeexplorer.data.remote.AnimeApi
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
    fun provideAnimeRepository(
        api: AnimeApi,
        database: AnimeDatabase
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            api,
            database
        )
    }
}