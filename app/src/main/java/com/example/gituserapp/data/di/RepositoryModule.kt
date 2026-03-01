package com.example.gituserapp.data.di

import com.example.gituserapp.data.repository.GitRepositoryImpl
import com.example.gituserapp.domain.repository.GitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGitRepository(
        githubRepositoryImpl: GitRepositoryImpl,
    ): GitRepository
}
