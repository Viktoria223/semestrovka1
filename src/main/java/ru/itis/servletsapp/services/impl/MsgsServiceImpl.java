package ru.itis.servletsapp.services.impl;

import ru.itis.servletsapp.dao.MsgRepository;
import ru.itis.servletsapp.dto.MsgDto;
import ru.itis.servletsapp.model.Msg;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.MsgsService;

import java.util.List;
import java.util.stream.Collectors;

public class MsgsServiceImpl implements MsgsService {
    private final MsgRepository msgRepository;

    public MsgsServiceImpl(MsgRepository msgRepository) {
        this.msgRepository = msgRepository;
    }

    @Override
    public List<MsgDto> getByDialogId(Long dialogId) {
        return msgRepository.findByAuthorId(dialogId).stream()
                .map(MsgDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public MsgDto addMsg(MsgDto msgDto) {
        Msg msg = Msg.builder()
                .id(msgDto.getId())
                .author(User.builder()
                        .id(msgDto.getAuthor().getId())
                        .avatarId(msgDto.getAuthor().getAvatarId())
                        .email(msgDto.getAuthor().getEmail())
                        .firstName(msgDto.getAuthor().getFirstName())
                        .lastName(msgDto.getAuthor().getLastName())
                        .build())
                .content(msgDto.getContent())
                .createdAt(msgDto.getCreatedAt())
                .dialogId(msgDto.getDialogId())
                .build();
        Msg savedMsg = msgRepository.save(msg);
        return MsgDto.from(savedMsg);
    }
}
