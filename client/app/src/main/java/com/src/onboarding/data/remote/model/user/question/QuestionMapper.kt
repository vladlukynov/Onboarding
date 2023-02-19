package com.src.onboarding.data.remote.model.user.question

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.di.NetworkModule
import com.src.onboarding.domain.model.user.Question

class QuestionMapper : Mapper<Question, QuestionResponse> {
    override suspend fun mapFromResponseToModel(data: QuestionResponse): Question {
        return Question(
            id = data.id,
            sender = data.sender,
            senderImg = "${NetworkModule.BASE_URL}${NetworkModule.USER_SERVICE_BASE_URL}${data.senderImg}",
            senderMessage = data.senderMessage,
            dateSenderMessage = data.dateSenderMessage,
            recipient = data.recipient,
            recipientImg = "${NetworkModule.BASE_URL}${NetworkModule.USER_SERVICE_BASE_URL}${data.recipientImg}",
            recipientMessage = data.recipientMessage,
            dateRecipientMessage = data.dateRecipientMessage
        )
    }
}