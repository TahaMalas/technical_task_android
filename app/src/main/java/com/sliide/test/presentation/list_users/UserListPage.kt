package com.sliide.test.presentation.list_users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sliide.test.domain.entity.UserEntity
import com.sliide.test.presentation.add_user.AddDialog
import com.sliide.test.presentation.user.UsersViewModel

@Composable
fun UserListPage(
    navController: NavController,
    viewModel: UsersViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val deleteDialog = remember { mutableStateOf<UserEntity?>(null) }

    val addDialog = remember {
        mutableStateOf<Boolean>(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.users) { user ->
                UserListItem(
                    user,
                    onItemLongClick = { user ->
                        deleteDialog.value = user
                    },
                )
            }
        }
        FloatingActionButton(
            onClick = {
                addDialog.value = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(
                Icons.Filled.Add,
                "",
            )

        }


        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        if (deleteDialog.value != null)
            DeleteDialog(
                onConfirm = {
                    viewModel.deleteUser(deleteDialog.value!!.id)
                    deleteDialog.value = null
                }, onDismiss = {
                    deleteDialog.value = null
                }
            )

        if (addDialog.value)
            AddDialog(onDismiss = {
                addDialog.value = false
            }, onConfirm = { email, name, gender ->
                run {
                    addDialog.value = false
                    viewModel.addUser(email, name, gender)
                }
            })

    }
}
