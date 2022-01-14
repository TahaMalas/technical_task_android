package com.sliide.test.data.datasources.remote

import com.sliide.test.data.model.BaseResponseModel
import com.sliide.test.data.model.UserModel
import retrofit2.Response
import retrofit2.http.*

interface UsersApi {

    @GET("users")
    suspend fun getUsers(): BaseResponseModel<List<UserModel>>

    @POST("users")
    @FormUrlEncoded
    suspend fun addUser(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("gender") gender: String,
        @Field("status") status: String = "active"
    ): Response<BaseResponseModel<UserModel>>

    @DELETE("users/{userID}")
    suspend fun deleteUser(@Path("userID") userID: Int): Response<Unit>

}
