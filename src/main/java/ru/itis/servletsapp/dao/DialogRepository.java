package ru.itis.servletsapp.dao;

import ru.itis.servletsapp.dao.base.CrudRepository;
import ru.itis.servletsapp.model.Dialog;
import ru.itis.servletsapp.model.User;

import java.util.List;
import java.util.Optional;

public interface DialogRepository extends CrudRepository<Dialog, Long> {
    List<User> findUsers(Long dialogId);
    Optional<Dialog> alreadyExist(Long userId1,Long userId2);
    List<Dialog> findByUserId(Long userId);
}
