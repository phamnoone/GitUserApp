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

class SearchUserUseCaseTest {

    private lateinit var gitRepository: GitRepository
    private lateinit var searchUserUseCase: SearchUserUseCase

    @Before
    fun setUp() {
        gitRepository = mockk()
        searchUserUseCase = SearchUserUseCase(gitRepository)
    }

    @Test
    fun `invoke should return user list when repository returns success`() = runTest {
        // Arrange
        val query = "test"
        val page = 1
        val expectedUsers = listOf(
            GitUser(1, "test user 1", "url1"),
            GitUser(2, "test user 2", "url2")
        )
        val expectedResponse = ApiResponse.Success(expectedUsers)
        
        coEvery { gitRepository.searchUser(query, page) } returns expectedResponse

        // Act
        val result = searchUserUseCase(query, page)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.searchUser(query, page) }
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        // Arrange
        val query = "invalid"
        val page = 2
        val expectedResponse = ApiResponse.Error.NetworkError<List<GitUser>>(Exception("Network Issue"))
        
        coEvery { gitRepository.searchUser(query, page) } returns expectedResponse

        // Act
        val result = searchUserUseCase(query, page)

        // Assert
        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { gitRepository.searchUser(query, page) }
    }
}
