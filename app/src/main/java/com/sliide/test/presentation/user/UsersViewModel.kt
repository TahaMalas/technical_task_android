package com.sliide.test.presentation.user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.test.core.Gender
import com.sliide.test.core.Resource
import com.sliide.test.domain.usecase.add_user.AddUser
import com.sliide.test.domain.usecase.delete_user.DeleteUser
import com.sliide.test.domain.usecase.get_users.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsers,
    private val deleteUserUseCase: DeleteUser,
    private val addUserUseCase: AddUser,
) : ViewModel() {
    private val _usersState = mutableStateOf(UsersState())
    val state: State<UsersState> = _usersState

    init {
        getUsers()
    }

    fun addUser(email: String, name: String, gender: Gender) {
        addUserUseCase(email, name, gender).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value.users.add(0, result.data!!)
                    _usersState.value = UsersState(isLoading = false, users = state.value.users)
                }
                is Resource.Loading -> {
                    _usersState.value = UsersState(isLoading = true, users = state.value.users)
                }
                is Resource.Error -> {
                    _usersState.value = UsersState(
                        isLoading = false,
                        error = result.message
                            ?: "Unexpected error occurred",
                        users = state.value.users,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun deleteUser(userID: Int) {
        _deleteUser(userID)
    }

    private fun _deleteUser(userID: Int) {
        deleteUserUseCase(userID).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value.users.removeAt(state.value.users.indexOfFirst {
                        it.id == userID
                    })
                    _usersState.value = UsersState(isLoading = false, users = state.value.users)
                }
                is Resource.Loading -> {
                    _usersState.value = UsersState(isLoading = true, users = state.value.users)
                }
                is Resource.Error -> {
                    _usersState.value = UsersState(
                        isLoading = false,
                        error = result.message
                            ?: "Unexpected error occurred",
                        users = state.value.users,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUsers() {
        getUsersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _usersState.value = UsersState(
                        isLoading = false, users = result.data?.toMutableList()
                            ?: mutableListOf()
                    )
                }
                is Resource.Loading -> {
                    _usersState.value = UsersState(isLoading = true)
                }
                is Resource.Error -> {
                    _usersState.value = UsersState(
                        isLoading = false, error = result.message
                            ?: "Unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
