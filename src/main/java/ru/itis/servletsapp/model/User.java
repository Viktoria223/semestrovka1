package ru.itis.servletsapp.model;

import lombok.*;
import ru.itis.servletsapp.dto.UserDto;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String hashPassword;
    private String email;
    private Integer age;
    private String gender;
    private String description;
    private Long avatarId;
    private List<User> matches;

    public static User from(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .description(userDto.getDescription())
                .lastName(userDto.getLastName())
                .avatarId(userDto.getAvatarId())
                .build();
    }
}
