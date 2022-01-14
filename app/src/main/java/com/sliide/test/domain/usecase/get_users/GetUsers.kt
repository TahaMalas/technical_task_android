package com.sliide.test.domain.usecase.get_users

import com.sliide.test.core.Resource
import com.sliide.test.data.model.toUser
import com.sliide.test.domain.entity.UserEntity
import com.sliide.test.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetUsers @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<Resource<List<UserEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val users = repository.getUsers()
            emit(Resource.Success(users.data.map { it.toUser() }))
        } catch (e: HttpException) {
            emit(Resource.Error<List<UserEntity>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<UserEntity>>("Couldn't reach server"))
        }
    }
}