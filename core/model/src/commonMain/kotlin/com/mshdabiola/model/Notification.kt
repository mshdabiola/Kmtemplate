package com.mshdabiola.model

sealed class Notification {
    data class Message(val message: String) : Notification()
    data class MessageWithAction(
        val message: String,
        val action: String,
        val actionCallback: () -> Unit,
    ) : Notification()
}
