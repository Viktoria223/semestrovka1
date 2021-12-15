package ru.itis.servletsapp.dao;

import ru.itis.servletsapp.dao.base.CrudRepository;
import ru.itis.servletsapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);

    List<Long> findMatchesId(Long id);

    Optional<User> getPair(Long id);

    void deleteToken(Long id);

    User findAll(Long id);

    void setInteractedWith(Long userId, Long interactedWith);

    void setMatch(Long userId, Long matchId);

    void updateAvatarForUser(Long userId, Long fileId);

    Optional<String> getTokenByUserId(Long userId);

    void createTokenForUser(Long userId, String token);

    void updateTokenForUser(Long userId, String token);
}
