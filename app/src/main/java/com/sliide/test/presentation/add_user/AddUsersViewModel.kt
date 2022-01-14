package com.sliide.test.presentation.add_user

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sliide.test.core.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddUsersViewModel @Inject constructor() : ViewModel() {

    private val _emailAddress = mutableStateOf("")
    val emailAddress: State<String> = _emailAddress

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _gender = mutableStateOf(Gender.MALE)
    val gender: State<Gender> = _gender

    fun onChangeEmail(email: String) {
        _emailAddress.value = email
    }


    fun onChangeName(name: String) {
        _name.value = name
    }


    fun onChangeGender(gender: Gender) {
        _gender.value = gender
    }

    fun isNameValid(): Boolean {
        return _name.value.length > 2
    }

    fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(_emailAddress.value).matches()
    }

}