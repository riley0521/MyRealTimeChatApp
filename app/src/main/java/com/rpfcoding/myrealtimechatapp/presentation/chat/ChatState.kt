package com.rpfcoding.myrealtimechatapp.presentation.chat

import com.rpfcoding.myrealtimechatapp.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)