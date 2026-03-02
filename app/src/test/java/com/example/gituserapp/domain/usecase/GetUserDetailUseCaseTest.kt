package com.example.gituserapp.domain.usecase

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitUserDetail
import com.example.gituserapp.domain.repository.GitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserDetailUseCaseTest {

    private lateinit var gitRepository: GitRepository
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        gitRepository = mockk()
        getUserDetailUseCase = GetUserDetailUseCase(gitRepository)
    }

    @Test
    fun `invoke should return user detail when repository returns success`() = runTest {
        // Arrange
        val username = "testuser"
        val expectedDetail = GitUserDetail(
            id = 1,
            login = username,
            avatarUrl = "url",
            name = "Test User",
            bio = "bio",
            followers = 10,
            location = "Tokyo",
            website = "test.com",
            email = "test@test.com"
        )
        val expectedResponse = ApiResponse.Success(expectedDetail)
        
        coEvery { gitRepository.getUserDetail(username) } returns expectedResponse

        // Act
        val result = getUserDetailUseCase(username)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.getUserDetail(username) }
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        // Arrange
        val username = "testuser"
        val expectedResponse = ApiResponse.Error.HttpError<GitUserDetail>(404, "Not Found")
        
        coEvery { gitRepository.getUserDetail(username) } returns expectedResponse

        // Act
        val result = getUserDetailUseCase(username)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.getUserDetail(username) }
    }
}
