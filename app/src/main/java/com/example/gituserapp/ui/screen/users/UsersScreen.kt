package com.example.gituserapp.ui.screen.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gituserapp.ui.base.BaseScreen
import com.example.gituserapp.ui.screen.users.components.UserItem
import com.example.gituserapp.ui.screen.users.components.SearchBar
import com.gituserapp.R

@Composable
fun UsersScreen(
    onUserClick: (String) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                onSearchKeywordChanged = { keyword ->
                    searchQuery = keyword
                    viewModel.onInput(UsersContract.Intent.SearchUsers(keyword))
                }
            )

            BaseScreen(
                uiState = uiState,
                onInitial = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(

                            text = stringResource(R.string.enter_keyword_to_search),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                onSuccess = { state ->
                    if (state.users.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_matching_users),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            itemsIndexed(state.users) { index, user ->
                                if (index >= state.users.size - 1 && !state.isLoadMore && !state.isReachedEnd) {
                                    LaunchedEffect(index) {
                                        viewModel.onInput(UsersContract.Intent.LoadMore)
                                    }
                                }
                                UserItem(
                                    avatarUrl = user.avatarUrl ?: "",
                                    name = user.name ?: stringResource(R.string.unknown),
                                    onClick = {
                                        onUserClick(user.name ?: "")
                                    }
                                )
                            }

                            if (state.isLoadMore) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}
