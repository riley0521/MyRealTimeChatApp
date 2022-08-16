package com.rpfcoding.myrealtimechatapp.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun ChatScreen(
    username: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connect()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(state.messages) { message ->
                ChatItem(
                    username,
                    message
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = viewModel.messageText,
                onValueChange = viewModel::onMessageChange,
                placeholder = {
                    Text(text = "Enter a message")
                },
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(onClick = viewModel::sendMessage) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}
