package com.rpfcoding.myrealtimechatapp.presentation.username

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor(): ViewModel() {

    var usernameText by mutableStateOf("")
        private set

    private val _onJoinChat = MutableSharedFlow<String>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    fun onUsernameChange(username: String) {
        usernameText = username
    }

    fun onJoinClick() {
        viewModelScope.launch {
            if(usernameText.isNotBlank()) {
                _onJoinChat.emit(usernameText)
            }
        }
    }
}