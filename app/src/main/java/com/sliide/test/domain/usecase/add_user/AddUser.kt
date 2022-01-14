package com.sliide.test.domain.usecase.add_user

import com.sliide.test.core.Gender
import com.sliide.test.core.Resource
import com.sliide.test.data.model.toUser
import com.sliide.test.domain.entity.UserEntity
import com.sliide.test.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AddUser @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(email: String, name: String, gender: Gender): Flow<Resource<UserEntity?>> =
        flow {
            try {
                emit(Resource.Loading<UserEntity?>())
                val response = repository.addUser(email, name, gender)

                if (response.code() == 201)
                    emit(Resource.Success<UserEntity?>(response.body()!!.data.toUser()))
                else
                    emit(Resource.Error<UserEntity?>("An unexpected error occurred"))

            } catch (e: HttpException) {
                emit(
                    Resource.Error<UserEntity?>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<UserEntity?>("Couldn't reach server"))
            }
        }
}