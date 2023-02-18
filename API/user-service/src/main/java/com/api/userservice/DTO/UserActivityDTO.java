package com.api.userservice.DTO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityDTO {
    private Long id;

    private String content;

    private String percent;

    private String date;

    public UserActivityDTO(Long id, String content, String percent, Date date) {
        this.id = id;
        this.content = content;
        this.percent = percent;
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        this.date = df.format(date);
    }
}