package com.example.gituserapp.domain.usecase

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitRepo
import com.example.gituserapp.domain.repository.GitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserReposUseCaseTest {

    private lateinit var gitRepository: GitRepository
    private lateinit var getUserReposUseCase: GetUserReposUseCase

    @Before
    fun setUp() {
        gitRepository = mockk()
        getUserReposUseCase = GetUserReposUseCase(gitRepository)
    }

    @Test
    fun `invoke should return repo list when repository returns success`() = runTest {
        // Arrange
        val username = "testuser"
        val page = 1
        val expectedRepos = listOf(
            GitRepo(1, "repo1", "desc", "public", 10, 5, "Kotlin", "url"),
            GitRepo(2, "repo2", "desc", "public", 20, 2, "Java", "url")
        )
        val expectedResponse = ApiResponse.Success(expectedRepos)
        
        coEvery { gitRepository.getUserRepos(username, page) } returns expectedResponse

        // Act
        val result = getUserReposUseCase(username, page)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.getUserRepos(username, page) }
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        // Arrange
        val username = "testuser"
        val page = 2
        val expectedResponse = ApiResponse.Error.HttpError<List<GitRepo>>(500, "Server Error")
        
        coEvery { gitRepository.getUserRepos(username, page) } returns expectedResponse

        // Act
        val result = getUserReposUseCase(username, page)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.getUserRepos(username, page) }
    }
}
