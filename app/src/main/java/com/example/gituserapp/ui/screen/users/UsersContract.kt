package com.example.gituserapp.ui.screen.users

import com.example.gituserapp.domain.model.GitUser
import com.example.gituserapp.ui.base.Input
import com.example.gituserapp.ui.base.Output

class UsersContract {

    sealed class Intent : Input {
        data class SearchUsers(val keyword: String) : Intent()
        object LoadMore : Intent()
    }

    sealed class Effect : Output {
    }

    data class State(
        val users: List<GitUser> = emptyList(),
        val isLoadMore: Boolean = false,
        val currentPage: Int = 1,
        val currentQuery: String = "",
        val isReachedEnd: Boolean = false
    )
}
