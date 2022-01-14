package com.sliide.test.presentation.add_user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sliide.test.core.Gender
import com.sliide.test.core.toCamelCase

@Composable
fun AddDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Gender) -> Unit,
    viewModel: AddUsersViewModel = hiltViewModel()
) {

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    isError = !viewModel.isEmailValid() && viewModel.emailAddress.value.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    value = viewModel.emailAddress.value,
                    singleLine = true,
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.body2,
                    onValueChange = {
                        println("email $it")
                        viewModel.onChangeEmail(it)
                    },
                    label = { Text("Email", style = MaterialTheme.typography.body1) },
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    isError = !viewModel.isNameValid() && viewModel.name.value.isNotEmpty(),
                    value = viewModel.name.value,
                    textStyle = MaterialTheme.typography.body2,
                    singleLine = true,
                    maxLines = 1,
                    onValueChange = {
                        viewModel.onChangeName(it)
                    },
                    label = { Text("Name", style = MaterialTheme.typography.body1) },
                    modifier = Modifier.fillMaxWidth(),
                )
                GenderDropDown(viewModel)
            }
        },
        confirmButton = {
            Button(
                enabled = viewModel.isNameValid() && viewModel.isEmailValid(),
                onClick = {
                    onConfirm(
                        viewModel.emailAddress.value,
                        viewModel.name.value,
                        viewModel.gender.value
                    )
                }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }) {
                Text("Close")
            }
        }
    )

}

@Composable
fun GenderDropDown(viewModel: AddUsersViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val items = Gender.values()
    val selectedIndex = remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            items[selectedIndex.value].toCamelCase(),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded.value = true })
                .padding(20.dp)
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    viewModel.onChangeGender(items[selectedIndex.value])
                    selectedIndex.value = index
                    expanded.value = false
                }) {
                    Text(
                        text = s.toCamelCase(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}