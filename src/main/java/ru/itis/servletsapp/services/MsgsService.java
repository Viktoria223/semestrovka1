package ru.itis.servletsapp.services;

import ru.itis.servletsapp.dto.MsgDto;

import java.util.List;

public interface MsgsService {
    List<MsgDto> getByDialogId(Long dialogId);
    MsgDto addMsg(MsgDto MsgDto);
}
