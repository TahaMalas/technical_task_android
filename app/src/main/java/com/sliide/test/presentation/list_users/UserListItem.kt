package com.sliide.test.presentation.list_users

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sliide.test.domain.entity.UserEntity


@Composable
fun UserListItem(
    user: UserEntity,
    onItemLongClick: (UserEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onItemLongClick(user)
                    }
                )
            }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
            )
        }

    }
}