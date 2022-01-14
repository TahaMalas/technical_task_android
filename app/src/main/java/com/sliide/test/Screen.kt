package com.sliide.test

sealed class Screen(val route: String) {
    object UsersListScreen : Screen("users_list_screen")
}