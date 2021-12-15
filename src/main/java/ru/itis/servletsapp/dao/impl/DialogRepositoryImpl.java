package ru.itis.servletsapp.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.servletsapp.dao.DialogRepository;
import ru.itis.servletsapp.dao.MsgRepository;
import ru.itis.servletsapp.model.Dialog;
import ru.itis.servletsapp.model.Msg;
import ru.itis.servletsapp.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DialogRepositoryImpl implements DialogRepository {

    private final static String SQL_INSERT = "insert into dialogs(user_1, user_2,last_message) " +
            "values (?, ?, ?)";
    private final static String SQL_UPDATE_TIME = "update dialogs set last_message = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from dialogs where id = ?";
    private final static String SQL_SELECT_ALL = "select * from dialogs order by last_message desc";
    private final static String SQL_SELECT_BY_USER_ID = "select * from dialogs where user_1 = ? OR user_2 = ? order by last_message desc";
    private final static String SQL_SELECT_USER1 = "select u.* from dialogs d join users u on (d.user_1 = u.id) where d.id = ?";
    private final static String SQL_SELECT_USER2 = "select u.* from dialogs d join users u on (d.user_2 = u.id) where d.id = ?";
    private final static String SQL_SELECT_BY_USER = "select * from dialogs where dialogs.user_1 = ? AND dialogs.user_2 = ? OR dialogs.user_1 = ? AND dialogs.user_2 = ?";

    private final RowMapper<Dialog> rowMapper = (row, rowNumber) -> Dialog.builder()
            .id(row.getLong("id"))
            .user1(row.getLong("user_1"))
            .user2(row.getLong("user_2"))
            .lastMsg(row.getTimestamp("last_message"))
            .build();

    private final RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getLong("id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .age(row.getInt("age"))
                    .gender(row.getString("gender"))
                    .description(row.getString("description"))
                    .email(row.getString("email"))
                    .avatarId(row.getLong("avatar_id") == 0 ? null : row.getLong("avatar_id"))
                    .build();


    private final JdbcTemplate jdbcTemplate;

    public DialogRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Dialog> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Dialog> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Dialog save(Dialog item) {
        if (item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setLong(1, item.getUser1());
                statement.setLong(2, item.getUser2());
                statement.setTimestamp(3, item.getLastMsg());
                return statement;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                item.setId(keyHolder.getKey().longValue());
            }
        } else {
            jdbcTemplate.update(SQL_UPDATE_TIME,
                    item.getLastMsg(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<User> findUsers(Long dialogId) {
        List<User> list = new LinkedList<>();
        list.add(jdbcTemplate.queryForObject(SQL_SELECT_USER1, userRowMapper, dialogId));
        list.add(jdbcTemplate.queryForObject(SQL_SELECT_USER2, userRowMapper, dialogId));
        return list;
    }

    @Override
    public Optional<Dialog> alreadyExist(Long userId1, Long userId2) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_USER, rowMapper, userId1, userId2, userId2, userId1));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Dialog> findByUserId(Long userId) {
        return jdbcTemplate.query(SQL_SELECT_BY_USER_ID, rowMapper,userId,userId);
    }
}
