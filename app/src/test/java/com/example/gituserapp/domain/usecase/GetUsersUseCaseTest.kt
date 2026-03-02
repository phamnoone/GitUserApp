package com.example.gituserapp.domain.usecase

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitUser
import com.example.gituserapp.domain.repository.GitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var gitRepository: GitRepository
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        gitRepository = mockk()
        getUsersUseCase = GetUsersUseCase(gitRepository)
    }

    @Test
    fun `invoke should return user list when repository returns success`() = runTest {
        // Arrange
        val nextKey = 10
        val expectedUsers = listOf(
            GitUser(1, "user1", "url1"),
            GitUser(2, "user2", "url2")
        )
        val expectedResponse = ApiResponse.Success(expectedUsers)
        
        coEvery { gitRepository.getUsers(nextKey) } returns expectedResponse

        // Act
        val result = getUsersUseCase(nextKey)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.getUsers(nextKey) }
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        // Arrange
        val nextKey = 20
        val expectedResponse = ApiResponse.Error.HttpError<List<GitUser>>(403, "Forbidden")
        
        coEvery { gitRepository.getUsers(nextKey) } returns expectedResponse

        // Act
        val result = getUsersUseCase(nextKey)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.getUsers(nextKey) }
    }
}
