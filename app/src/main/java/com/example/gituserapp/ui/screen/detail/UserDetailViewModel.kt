package com.example.gituserapp.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.usecase.GetUserDetailUseCase
import com.example.gituserapp.domain.usecase.GetUserReposUseCase
import com.example.gituserapp.ui.base.BaseViewModel
import com.example.gituserapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserReposUseCase: GetUserReposUseCase,
) : BaseViewModel<UserDetailContract.State, UserDetailContract.Intent, UserDetailContract.Effect>() {

    private var loadMoreJob: Job? = null

    override fun createInitialState(): UiState<UserDetailContract.State> = UiState.Initial

    override fun handleInput(intent: UserDetailContract.Intent) {
        when (intent) {
            is UserDetailContract.Intent.LoadUserDetail -> loadUserDetailAndRepos(intent.username)
            is UserDetailContract.Intent.LoadMoreRepos -> {
                val currentState = (uiState.value as? UiState.Success)?.data ?: return
                if (!currentState.isLoadMore && !currentState.isReachedEnd && currentState.currentUsername.isNotBlank()) {
                    loadMoreRepos(currentState.currentUsername, currentState.currentPage + 1)
                }
            }
        }
    }

    private fun loadUserDetailAndRepos(username: String) {
        setState { UiState.Loading }
        
        viewModelScope.launch {
            val userDetailDeferred = async { getUserDetailUseCase(username) }
            val reposDeferred = async { getUserReposUseCase(username, 1) }

            val userDetailResponse = userDetailDeferred.await()
            val reposResponse = reposDeferred.await()

            if (userDetailResponse is ApiResponse.Success) {
                val reposData = if (reposResponse is ApiResponse.Success) reposResponse.data else emptyList()
                
                setState {
                    UiState.Success(
                        UserDetailContract.State(
                            userDetail = userDetailResponse.data,
                            repos = reposData,
                            isLoadMore = false,
                            currentPage = 1,
                            currentUsername = username,
                            isReachedEnd = reposData.isEmpty()
                        )
                    )
                }
            } else {
                setState { UiState.Error(ApiResponse.Error.UnknownError<UserDetailContract.State>(Exception())) }
            }
        }
    }

    private fun loadMoreRepos(username: String, nextPage: Int) {
        if (loadMoreJob?.isActive == true) return

        loadMoreJob = viewModelScope.launch {
            val currentState = (uiState.value as? UiState.Success)?.data ?: return@launch
            setState { UiState.Success(currentState.copy(isLoadMore = true)) }

            when (val response = getUserReposUseCase(username = username, page = nextPage)) {
                is ApiResponse.Success -> {
                    if (response.data.isEmpty()) {
                        setState { UiState.Success(currentState.copy(isLoadMore = false, isReachedEnd = true)) }
                    } else {
                        setState {
                            UiState.Success(
                                currentState.copy(
                                    repos = currentState.repos + response.data,
                                    isLoadMore = false,
                                    currentPage = nextPage,
                                    isReachedEnd = false
                                )
                            )
                        }
                    }
                }
                is ApiResponse.Error -> {
                    setState { 
                        UiState.Success(
                            currentState.copy(
                                isLoadMore = false
                            )
                        ) 
                    }
                    emitOutput { UserDetailContract.Effect.ShowLoadMoreError }
                }
            }
        }
    }
}
