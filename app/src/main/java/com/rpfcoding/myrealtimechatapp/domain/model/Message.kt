package com.rpfcoding.myrealtimechatapp.domain.model

import java.time.LocalDate

data class Message(
    val text: String,
    val formattedTime: String,
    val username: String,
)
