package com.sliide.test.di

import com.sliide.test.core.Constants
import com.sliide.test.data.datasources.remote.UsersApi
import com.sliide.test.data.repository.UserRepositoryImpl
import com.sliide.test.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val headerInterceptor = HttpLoggingInterceptor()
        headerInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val bodyInterceptor = HttpLoggingInterceptor()
        bodyInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader(
                    "Authorization",
                    "Bearer ${Constants.TOKEN}"
                ).build()
                chain.proceed(request)
            }
            .addInterceptor(headerInterceptor)
            .addInterceptor(bodyInterceptor)
            .build()

    }

    @Provides
    @Singleton
    fun providesUsersAPI(okHttpClient: OkHttpClient): UsersApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UsersApi::class.java)

    }

    @Provides
    @Singleton
    fun provideUsersRepository(api: UsersApi): UserRepository {
        return UserRepositoryImpl(api)
    }


}