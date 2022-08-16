package com.rpfcoding.myrealtimechatapp.data.remote

import com.rpfcoding.myrealtimechatapp.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/"
    }

    sealed class Endpoints(val url: String) {
        object GetAllMessages: Endpoints("$BASE_URL/messages")
        object ChatS
    }
}