package com.src.onboarding.data.remote.model.user.question

@kotlinx.serialization.Serializable
data class QuestionResponse(
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