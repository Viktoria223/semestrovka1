package ru.itis.servletsapp.services;

import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    boolean isInMatch(Long userId, Long matchId);

    void setDislike(Long userId, Long matchId);

    User getPair(Long id);

    void deleteToken(Long id);

    void setMatch(Long userId, Long matchId);
}
