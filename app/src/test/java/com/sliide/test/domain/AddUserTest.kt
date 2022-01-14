package com.sliide.test.domain

import com.sliide.test.core.Gender
import com.sliide.test.core.Resource
import com.sliide.test.core.TestCoroutineRule
import com.sliide.test.core.test
import com.sliide.test.data.model.BaseResponseModel
import com.sliide.test.data.model.UserModel
import com.sliide.test.data.model.toUser
import com.sliide.test.domain.entity.UserEntity
import com.sliide.test.domain.repository.UserRepository
import com.sliide.test.domain.usecase.add_user.AddUser
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
class AddUserTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var addUser: AddUser
    private val mockRepository = mock(UserRepository::class.java)
    private val tEmail = "tahamalas@live.com"
    private val tName = "Taha Malas"
    private val tEnumGender = Gender.MALE
    private val tGender = "Male"
    private val tStatus = "active"
    private val tUserID = 1
    private val tUserModel = UserModel(tEmail, tGender, tUserID, tName, tStatus)
    private val tUserEntity = tUserModel.toUser()
    private val successUserResponse = BaseResponseModel(tUserModel)
    private val unexpectedError = "An unexpected error occurred"
    private val couldNotReachServer = "Couldn't reach server"


    @Before
    fun setup() {
        addUser = AddUser(mockRepository)
    }


    @Test
    fun `add user successfully`() {
        testCoroutineRule.runBlockingTest {
            val response = success(201, successUserResponse)
            `when`(mockRepository.addUser(tEmail, tName, tEnumGender)).thenReturn(response)
            val result = addUser.invoke(tEmail, tName, tEnumGender)
            result
                .test<Resource<UserEntity?>>(this)
                .assertValues(
                    Resource.Loading<UserEntity?>(),
                    Resource.Success<UserEntity?>(tUserEntity),
                )
                .finish()
            verify(mockRepository).addUser(tEmail, tName, tEnumGender)
        }
        verifyNoMoreInteractions(mockRepository)
    }

    @Test
    fun `add user bad request`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Invalid name".toResponseBody("application/json".toMediaTypeOrNull())
            val response = retrofit2.Response.error<BaseResponseModel<UserModel>>(400, errorMessage)
            `when`(mockRepository.addUser(tEmail, tName, tEnumGender)).thenReturn(response)
            val result = addUser.invoke(tEmail, tName, tEnumGender)
            result
                .test<Resource<UserEntity?>>(this)
                .assertValues(
                    Resource.Loading<UserEntity?>(),
                    Resource.Error<UserEntity?>(unexpectedError),
                )
                .finish()
            verify(mockRepository).addUser(tEmail, tName, tEnumGender)
        }
        verifyNoMoreInteractions(mockRepository)
    }


    @Test
    fun `add user CouldNotReachServer exception`() {
        testCoroutineRule.runBlockingTest {
            `when`(mockRepository.addUser(tEmail, tName, tEnumGender)).thenAnswer {
                throw IOException()
            }
            val result = addUser.invoke(tEmail, tName, tEnumGender)
            result
                .test<Resource<UserEntity?>>(this)
                .assertValues(
                    Resource.Loading<UserEntity?>(),
                    Resource.Error<UserEntity?>(couldNotReachServer),
                )
                .finish()
            verify(mockRepository).addUser(tEmail, tName, tEnumGender)
        }
        verifyNoMoreInteractions(mockRepository)
    }

}