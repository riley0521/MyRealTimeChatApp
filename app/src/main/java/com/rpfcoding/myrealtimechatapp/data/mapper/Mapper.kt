package com.rpfcoding.myrealtimechatapp.data.mapper

import com.rpfcoding.myrealtimechatapp.data.remote.dto.MessageDto
import com.rpfcoding.myrealtimechatapp.domain.model.Message
import java.text.DateFormat
import java.util.*

fun MessageDto.toMessage(): Message {
    val date = Date(timestamp)
    val formattedTime = DateFormat
        .getTimeInstance(DateFormat.DEFAULT)
        .format(date)
    return Message(
        text = text,
        formattedTime = formattedTime,
        username = username
    )
}