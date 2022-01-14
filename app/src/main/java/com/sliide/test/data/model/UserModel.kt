package com.sliide.test.data.model

import com.sliide.test.domain.entity.UserEntity

data class UserModel(
        val email: String,
        val gender: String,
        val id: Int,
        val name: String,
        val status: String
)

fun UserModel.toUser(): UserEntity {
    return UserEntity(
            email = email,
            id = id,
            name = name,
    )
}