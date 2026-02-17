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

    /**
     * Provides the AnimeRepository.
     * Since AnimeRepository uses constructor injection with @Inject,
     * Hilt can also provide it automatically if the class is annotated correctly.
     * However, defining it here allows for easier swapping with mock repositories in tests.
     */
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