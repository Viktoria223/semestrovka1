package ru.itis.servletsapp.model;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Dialog {
    Long id;
    Long user1;
    Long user2;
    Timestamp lastMsg;
}