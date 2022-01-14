package com.sliide.test.domain.repository

import com.sliide.test.core.Gender
import com.sliide.test.data.model.BaseResponseModel
import com.sliide.test.data.model.UserModel
import retrofit2.Response

interface UserRepository {
    suspend fun getUsers(): BaseResponseModel<List<UserModel>>

    suspend fun deleteUser(userID: Int): Response<Unit>

    suspend fun addUser(email: String, name: String, gender: Gender): Response<BaseResponseModel<UserModel>>
}