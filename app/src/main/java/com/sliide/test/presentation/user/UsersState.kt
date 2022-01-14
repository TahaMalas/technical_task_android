package com.sliide.test.presentation.user

import com.sliide.test.domain.entity.UserEntity

data class UsersState(
    val isLoading: Boolean = true,
    val users: MutableList<UserEntity> = mutableListOf(),
    val error: String = "",
)
//
//class UsersState private constructor(
//    var isLoading: Boolean?,
//    var users: MutableList<UserEntity>?,
//    var error: String?,
//) {
//
//    data class Builder(
//        var isLoading: Boolean? = null,
//        var users: MutableList<UserEntity>? = null,
//        var error: String? = null,
//    ) {
//
//        fun isLoading(isLoading: Boolean) = apply { this.isLoading = isLoading }
//        fun users(users: MutableList<UserEntity>) = apply { this.users = users }
//        fun error(error: String?) = apply { this.error = error }
//        fun build() = UsersState(isLoading, users, error)
//    }
//}