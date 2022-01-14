package com.sliide.test.domain.usecase.delete_user

import com.sliide.test.core.Resource
import com.sliide.test.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DeleteUser @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(userID: Int): Flow<Resource<Any?>> = flow {
        try {
            emit(Resource.Loading<Any?>())
            val response = repository.deleteUser(userID = userID)
            if (response.code() == 204)
                emit(Resource.Success<Any?>(null))
            else
                emit(Resource.Error<Any?>("An unexpected error occurred"))

        } catch (e: HttpException) {
            emit(Resource.Error<Any?>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<Any?>("Couldn't reach server"))
        }
    }
}