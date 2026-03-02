package com.example.gituserapp.ui.screen.users

import androidx.lifecycle.viewModelScope
import com.example.gituserapp.base.KeyConstant
import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.usecase.GetUsersUseCase
import com.example.gituserapp.domain.usecase.SearchUserUseCase
import com.example.gituserapp.ui.base.BaseViewModel
import com.example.gituserapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<UsersContract.State, UsersContract.Intent, UsersContract.Effect>() {

    private val _searchKeyword = MutableStateFlow("")
    private var searchJob: Job? = null
    private var loadMoreJob: Job? = null

    init {
        viewModelScope.launch {
            _searchKeyword
                .debounce(KeyConstant.SEARCH_DEBOUNCE_TIME)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        searchUsers(query)
                    } else {
                        getInitialUsers()
                    }
                }
        }
    }

    override fun createInitialState(): UiState<UsersContract.State> = UiState.Initial

    override fun handleInput(intent: UsersContract.Intent) {
        when (intent) {
            is UsersContract.Intent.SearchUsers -> {
                _searchKeyword.value = intent.keyword
            }

            is UsersContract.Intent.LoadMore -> {
                val currentState = (uiState.value as? UiState.Success)?.data ?: return
                if (!currentState.isLoadMore && !currentState.isReachedEnd) {
                    if (_searchKeyword.value.isNotBlank()) {
                        loadMoreSearchUsers(currentState.currentQuery, currentState.currentPage + 1)
                    } else {
                        loadMoreGeneralUsers()
                    }
                }
            }
        }
    }

    private fun getInitialUsers() {
        searchJob?.cancel()
        loadMoreJob?.cancel()

        searchJob = viewModelScope.launch {
            setState { UiState.Loading }

            when (val response = getUsersUseCase(0)) {
                is ApiResponse.Success -> {
                    setState {
                        UiState.Success(
                            UsersContract.State(
                                users = response.data,
                                isLoadMore = false,
                                currentPage = 1,
                                currentQuery = "",
                                isReachedEnd = response.data.isEmpty()
                            )
                        )
                    }
                }

                is ApiResponse.Error -> {
                    setState { UiState.Error(response) }
                }
            }
        }
    }

    private fun searchUsers(keyword: String) {
        searchJob?.cancel()
        loadMoreJob?.cancel()

        searchJob = viewModelScope.launch {
            val response = searchUserUseCase(keyword = keyword, page = 1)
            when (response) {
                is ApiResponse.Success -> {
                    setState {
                        UiState.Success(
                            UsersContract.State(
                                users = response.data,
                                isLoadMore = false,
                                currentPage = 1,
                                currentQuery = keyword,
                                isReachedEnd = response.data.isEmpty()
                            )
                        )
                    }
                }

                is ApiResponse.Error -> {
                    setState { UiState.Error(response) }
                }
            }
        }
    }

    private fun loadMoreGeneralUsers() {
        if (loadMoreJob?.isActive == true) return

        loadMoreJob = viewModelScope.launch {
            val currentState = (uiState.value as? UiState.Success)?.data ?: return@launch
            val nextSince = currentState.users.lastOrNull()?.id
            if (nextSince == null) return@launch

            setState { UiState.Success(currentState.copy(isLoadMore = true)) }
            when (val response = getUsersUseCase(nextKey = nextSince.toInt())) {
                is ApiResponse.Success -> {
                    if (response.data.isEmpty()) {
                        setState {
                            UiState.Success(
                                currentState.copy(
                                    isLoadMore = false,
                                    isReachedEnd = true
                                )
                            )
                        }
                    } else {
                        setState {
                            UiState.Success(
                                currentState.copy(
                                    users = currentState.users + response.data,
                                    isLoadMore = false,
                                    isReachedEnd = false
                                )
                            )
                        }
                    }
                }

                is ApiResponse.Error -> {
                    setState { UiState.Success(currentState.copy(isLoadMore = false)) }
                }
            }
        }
    }

    private fun loadMoreSearchUsers(keyword: String, nextPage: Int) {
        if (loadMoreJob?.isActive == true) return

        loadMoreJob = viewModelScope.launch {
            val currentState = (uiState.value as? UiState.Success)?.data ?: return@launch

            setState { UiState.Success(currentState.copy(isLoadMore = true)) }

            when (val response = searchUserUseCase(keyword = keyword, page = nextPage)) {
                is ApiResponse.Success -> {
                    if (response.data.isEmpty()) {
                        setState {
                            UiState.Success(
                                currentState.copy(
                                    isLoadMore = false,
                                    isReachedEnd = true
                                )
                            )
                        }
                    } else {
                        setState {
                            UiState.Success(
                                currentState.copy(
                                    users = currentState.users + response.data,
                                    isLoadMore = false,
                                    currentPage = nextPage,
                                    isReachedEnd = false
                                )
                            )
                        }
                    }
                }

                is ApiResponse.Error -> {
                    setState { UiState.Success(currentState.copy(isLoadMore = false)) }
                }
            }
        }
    }
}
