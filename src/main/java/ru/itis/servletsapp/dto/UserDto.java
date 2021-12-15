package ru.itis.servletsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.itis.servletsapp.model.User;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String description;
    private Long avatarId;
    private String token;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .description(user.getDescription())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarId(user.getAvatarId())
                .build();
    }
}
