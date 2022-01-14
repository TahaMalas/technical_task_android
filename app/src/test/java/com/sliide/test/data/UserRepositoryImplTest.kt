package com.sliide.test.data

import com.sliide.test.core.Gender
import com.sliide.test.data.datasources.remote.UsersApi
import com.sliide.test.data.model.BaseResponseModel
import com.sliide.test.data.model.UserModel
import com.sliide.test.data.repository.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response.success


class UserRepositoryImplTest {

    private lateinit var repo: UserRepositoryImpl
    private val mockRemoteDataSource: UsersApi = mock(UsersApi::class.java)
    private val tEmail = "tahamalas@live.com"
    private val tName = "Taha Malas"
    private val tEnumGender = Gender.MALE
    private val tGender = "Male"
    private val tStatus = "active"
    private val tUserID = 1
    private val tUser = UserModel(tEmail, tGender, tUserID, tName, tStatus)
    private val tUsersList = listOf(tUser)
    private val successUsersResponse = BaseResponseModel(tUsersList)
    private val successUserResponse = BaseResponseModel(tUser)


    @Before
    fun setup() {
        repo = UserRepositoryImpl(mockRemoteDataSource)
    }


    @Test
    fun `get users`() {
        runBlocking {
            val response = successUsersResponse

            `when`(mockRemoteDataSource.getUsers()).thenReturn(response)
            val result = repo.getUsers()
            verify(mockRemoteDataSource).getUsers()
            Assert.assertEquals(response, result)
            verifyNoMoreInteractions(mockRemoteDataSource)
        }
    }


    @Test
    fun `delete user`() {
        runBlocking {
            val response = success(Unit)


            `when`(mockRemoteDataSource.deleteUser(tUserID)).thenReturn(response)
            val result = repo.deleteUser(tUserID)
            verify(mockRemoteDataSource).deleteUser(tUserID)
            Assert.assertEquals(response, result)
            verifyNoMoreInteractions(mockRemoteDataSource)
        }
    }


    @Test
    fun `add user`() {
        runBlocking {
            val response = success(successUserResponse)

            `when`(mockRemoteDataSource.addUser(tEmail, tName, tGender, tStatus)).thenReturn(
                response
            )
            val result = repo.addUser(tEmail, tName, tEnumGender)
            verify(mockRemoteDataSource).addUser(tEmail, tName, tGender)
            Assert.assertEquals(response, result)
            verifyNoMoreInteractions(mockRemoteDataSource)
        }
    }
}