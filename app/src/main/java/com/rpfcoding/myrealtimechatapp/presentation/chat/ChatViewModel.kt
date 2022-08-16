package com.rpfcoding.myrealtimechatapp.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.myrealtimechatapp.data.remote.ChatSocketService
import com.rpfcoding.myrealtimechatapp.data.remote.MessageService
import com.rpfcoding.myrealtimechatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
    private val savedState: SavedStateHandle
) : ViewModel() {

    var messageText by mutableStateOf("")
        private set

    var state by mutableStateOf(ChatState())
        private set

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun onMessageChange(message: String) {
        messageText = message
    }

    fun connect() {
        getAllMessages()
        savedState.get<String>("username")?.let {
            viewModelScope.launch {
                val result = chatSocketService.initSession(it)
                when (result) {
                    is Resource.Success -> {
                        chatSocketService.observeMessages()
                            .onEach { message ->
                                val newList = state.messages.toMutableList().apply {
                                    add(0, message)
                                }

                                state = state.copy(
                                    messages = newList
                                )
                            }.launchIn(viewModelScope)
                    }
                    is Resource.Error -> {
                        _toastEvent.emit(result.message ?: "Unknown Error")
                    }
                }
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            state = state.copy(
                messages = messageService.getAllMessages(),
                isLoading = false
            )
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (messageText.isNotBlank()) {
                chatSocketService.sendMessage(messageText)
                messageText = ""
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}