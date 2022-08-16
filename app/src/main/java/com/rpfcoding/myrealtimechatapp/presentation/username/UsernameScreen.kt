package com.rpfcoding.myrealtimechatapp.presentation.username

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myrealtimechatapp.presentation.destinations.ChatScreenDestination
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination
@Composable
fun UsernameScreen(
    viewModel: UsernameViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    LaunchedEffect(key1 = true) {
        viewModel.onJoinChat.collectLatest { username ->
            navigator.navigate(ChatScreenDestination(username))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            TextField(
                value = viewModel.usernameText,
                onValueChange = viewModel::onUsernameChange,
                placeholder = {
                    Text(text = "Enter a username...")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = viewModel::onJoinClick) {
                Text(text = "Join")
            }
        }
    }
}