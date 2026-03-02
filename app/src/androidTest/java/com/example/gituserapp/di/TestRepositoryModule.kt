package com.example.gituserapp.di

import com.example.gituserapp.domain.repository.FakeGitRepository
import com.example.gituserapp.domain.repository.GitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import com.example.gituserapp.data.di.RepositoryModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGitRepository(
        fakeGitRepository: FakeGitRepository
    ): GitRepository
}
