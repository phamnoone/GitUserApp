package com.example.gituserapp.ui.screen.detail

import com.example.gituserapp.domain.model.GitRepo
import com.example.gituserapp.domain.model.GitUserDetail
import com.example.gituserapp.ui.base.Input
import com.example.gituserapp.ui.base.Output

class UserDetailContract {

    sealed class Intent : Input {
        data class LoadUserDetail(val username: String) : Intent()
        object LoadMoreRepos : Intent()
    }

    sealed class Effect : Output {
        object ShowLoadMoreError : Effect()
    }

    data class State(
        val userDetail: GitUserDetail? = null,
        val repos: List<GitRepo> = emptyList(),
        val isLoadMore: Boolean = false,
        val currentPage: Int = 1,
        val currentUsername: String = "",
        val isReachedEnd: Boolean = false
    )
}
