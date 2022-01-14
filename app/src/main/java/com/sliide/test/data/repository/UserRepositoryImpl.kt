package com.sliide.test.data.repository

import com.sliide.test.core.Gender
import com.sliide.test.core.toCamelCase
import com.sliide.test.data.datasources.remote.UsersApi
import com.sliide.test.data.model.BaseResponseModel
import com.sliide.test.data.model.UserModel
import com.sliide.test.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val remoteDataSource: UsersApi) :
    UserRepository {
    override suspend fun getUsers(): BaseResponseModel<List<UserModel>> {
        return remoteDataSource.getUsers()
    }

    override suspend fun deleteUser(userID: Int): Response<Unit> {
        return remoteDataSource.deleteUser(userID)
    }

    override suspend fun addUser(
        email: String,
        name: String,
        gender: Gender
    ): Response<BaseResponseModel<UserModel>> {
        return remoteDataSource.addUser(email, name, gender.toCamelCase())
    }

}