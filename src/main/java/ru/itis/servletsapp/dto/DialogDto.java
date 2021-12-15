package ru.itis.servletsapp.dto;


import lombok.*;
import ru.itis.servletsapp.model.Dialog;
import ru.itis.servletsapp.model.Msg;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DialogDto {
    private Long id;
    private Long user1;
    private Long user2;
    Timestamp lastMsg;

    public static DialogDto from(Dialog dialog) {
        return DialogDto.builder()
                .id(dialog.getId())
                .user1(dialog.getUser1())
                .user2(dialog.getUser2())
                .lastMsg(dialog.getLastMsg())
                .build();
    }
}