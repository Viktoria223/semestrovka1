package ru.itis.servletsapp.services;

import ru.itis.servletsapp.dto.MsgDto;
import ru.itis.servletsapp.model.Dialog;
import ru.itis.servletsapp.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DialogService {
    List<User> getUsers(Long dialogId);
    List<Dialog> getDialogs(Long userId);
    Optional<Dialog> alreadyExist(Long userId1, Long userId2);
    Dialog createDialog(Dialog dialog);
    Dialog getDialogById(Long dialogId);
    void updateTime(Long dialogId, Timestamp time);
}
