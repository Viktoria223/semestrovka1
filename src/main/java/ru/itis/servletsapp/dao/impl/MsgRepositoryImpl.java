package ru.itis.servletsapp.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.servletsapp.dao.MsgRepository;
import ru.itis.servletsapp.model.Msg;
import ru.itis.servletsapp.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class MsgRepositoryImpl implements MsgRepository {

    private final static String SQL_INSERT = "insert into messages(author_id, content, created_at, dialog_id) " +
            "values (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update messages set author_id = ?, content = ?, created_at = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select messages.id as msg_id, author_id, created_at, content, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from messages left join users on messages.author_id = users.id where messages.id = ?";
    private final static String SQL_SELECT_ALL = "select messages.id as msg_id, author_id, created_at, content, users.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from messages left join users on messages.author_id = users.id order by created_at desc";
    private final static String SQL_SELECT_BY_AUTHOR_ID = "select m.*, u.id as users_id, first_name, last_name, age, password_hash, email, avatar_id from dialogs d join messages m on d.id = m.dialog_id join users u on u.id = m.author_id where d.id = ? order by created_at desc";

    private final RowMapper<Msg> rowMapper = (row, rowNumber) -> Msg.builder()
            .id(row.getLong("id"))
            .author(
                    User.builder()
                            .id(row.getLong("users_id"))
                            .firstName(row.getString("first_name"))
                            .lastName(row.getString("last_name"))
                            .age(row.getInt("age"))
                            .hashPassword(row.getString("password_hash"))
                            .email(row.getString("email"))
                            .avatarId(row.getLong("avatar_id"))
                            .build()
            )
            .dialogId(row.getLong("dialog_id"))
            .content(row.getString("content"))
            .createdAt(row.getTimestamp("created_at"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public MsgRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Msg> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Msg> findByAuthorId(Long authorId) {
        return jdbcTemplate.query(SQL_SELECT_BY_AUTHOR_ID, rowMapper, authorId);
    }

    @Override
    public List<Msg> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Msg save(Msg item) {
        if (item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setLong(1, item.getAuthor().getId());
                statement.setString(2, item.getContent());
                statement.setTimestamp(3, item.getCreatedAt());
                statement.setLong(4, item.getDialogId());
                return statement;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                item.setId(keyHolder.getKey().longValue());
            }
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getAuthor().getId(),
                    item.getContent(),
                    item.getCreatedAt(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Long id) {
    }
}
