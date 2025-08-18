package com.mshdabiola.model

sealed class Notification(
    open val type: Type= Type.Default,
    open val duration: SnackbarDuration= SnackbarDuration.Short,

    ) {
    data class Message(
        override val type: Type= Type.Default,
        val message: String,
        override val duration: SnackbarDuration= SnackbarDuration.Short,
    ) : Notification(type,duration)
    data class MessageWithAction(
        override val type: Type= Type.Default,
        override val duration: SnackbarDuration= SnackbarDuration.Short,
        val message: String,
        val action: String,
        val actionCallback: () -> Unit,
    ) : Notification(type,duration)
}
enum class Type{
    Default, Error, Success, Warning
}

enum class SnackbarDuration {
    Short,
    Long,
    Indefinite,
}
