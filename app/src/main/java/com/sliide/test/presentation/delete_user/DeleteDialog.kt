package com.sliide.test.presentation.list_users

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Remove User", style = MaterialTheme.typography.h2)
        },
        text = {
            Text(
                "Are you sure you want to remove this user?",
                style = MaterialTheme.typography.body1
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }) {
                Text("Remove")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }) {
                Text("Cancel")
            }
        }
    )
}