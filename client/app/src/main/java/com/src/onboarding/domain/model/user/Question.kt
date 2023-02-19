package com.src.onboarding.domain.model.user

data class Question(
    val id: Long,
    val sender: String,
    val senderImg: String?,
    val senderMessage: String,
    val dateSenderMessage: String,
    val recipient: String?,
    val recipientImg: String?,
    val recipientMessage: String?,
    val dateRecipientMessage: String?
)