package ru.itis.servletsapp.dto;


import lombok.*;
import ru.itis.servletsapp.model.Msg;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MsgDto {
    private Long id;
    private UserDto author;
    private String content;
    private Long dialogId;
    private Timestamp createdAt;

    public static MsgDto from(Msg msg) {
        return MsgDto.builder()
                .id(msg.getId())
                .author(UserDto.from(msg.getAuthor()))
                .content(msg.getContent())
                .createdAt(msg.getCreatedAt())
                .dialogId(msg.getDialogId())
                .build();
    }
}