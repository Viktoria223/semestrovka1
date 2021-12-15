package ru.itis.servletsapp.dao;

import ru.itis.servletsapp.dao.base.CrudRepository;
import ru.itis.servletsapp.model.Msg;

import java.util.List;

public interface MsgRepository extends CrudRepository<Msg, Long> {
    List<Msg> findByAuthorId(Long authorId);
}
