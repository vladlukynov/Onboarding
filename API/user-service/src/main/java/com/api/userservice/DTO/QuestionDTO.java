package com.api.userservice.DTO;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.api.userservice.model.Question;
import com.api.userservice.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;

    private String sender;

    private String senderImg;

    private String senderMessage;

    private String dateSenderMessage;

    private String recipient;

    private String recipientImg;

    private String recipientMessage;

    private String dateRecipientMessage;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.sender = question.getSender().getName();
        this.senderImg = "/user/image?id=" + question.getSender().getId();
        this.senderMessage = question.getSenderMessage();
        this.dateSenderMessage = question.getDateSenderMessage();
        if(question.getRecipient() != null) {
            this.recipient = question.getRecipient().getName();
            this.recipientImg = "/user/image?id=" + question.getRecipient().getId();
        }
        this.recipientMessage = question.getRecipientMessage();
        this.dateRecipientMessage = question.getDateRecipientMessage();
    }

}
