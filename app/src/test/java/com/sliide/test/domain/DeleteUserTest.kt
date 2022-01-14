package com.sliide.test.domain

import com.sliide.test.core.Resource
import com.sliide.test.core.TestCoroutineRule
import com.sliide.test.core.test
import com.sliide.test.domain.repository.UserRepository
import com.sliide.test.domain.usecase.delete_user.DeleteUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response.success


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteUserTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var deleteUser: DeleteUser
    private val mockRepository = mock(UserRepository::class.java)
    private val tUserID = 1
    private val unexpectedError = "An unexpected error occurred"
    private val couldNotReachServer = "Couldn't reach server"


    @Before
    fun setup() {
        deleteUser = DeleteUser(mockRepository)
    }


    @Test
    fun `delete user successfully`() {
        testCoroutineRule.runBlockingTest {
            val response = success(204, Unit)
            `when`(mockRepository.deleteUser(tUserID)).thenReturn(response)
            val result = deleteUser.invoke(tUserID)
            result
                .test<Resource<Any?>>(this)
                .assertValues(
                    Resource.Loading<Any?>(),
                    Resource.Success<Any?>(null),
                )
                .finish()
            verify(mockRepository).deleteUser(tUserID)
        }
        verifyNoMoreInteractions(mockRepository)
    }

    @Test
    fun `delete user bad request`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage =
                "Not registered user".toResponseBody("application/json".toMediaTypeOrNull())
            val response = retrofit2.Response.error<Unit>(400, errorMessage)
            `when`(mockRepository.deleteUser(tUserID)).thenReturn(response)
            val result = deleteUser.invoke(tUserID)
            result
                .test<Resource<Any?>>(this)
                .assertValues(
                    Resource.Loading<Any?>(),
                    Resource.Error<Any?>(unexpectedError),
                )
                .finish()
            verify(mockRepository).deleteUser(tUserID)
        }
        verifyNoMoreInteractions(mockRepository)
    }


    @Test
    fun `delete user CouldNotReachServer exception`() {
        testCoroutineRule.runBlockingTest {
            `when`(mockRepository.deleteUser(tUserID)).thenAnswer {
                throw IOException()
            }
            val result = deleteUser.invoke(tUserID)
            result
                .test<Resource<Any?>>(this)
                .assertValues(
                    Resource.Loading<Any?>(),
                    Resource.Error<Any?>(couldNotReachServer),
                )
                .finish()
            verify(mockRepository).deleteUser(tUserID)
        }
        verifyNoMoreInteractions(mockRepository)
    }

}