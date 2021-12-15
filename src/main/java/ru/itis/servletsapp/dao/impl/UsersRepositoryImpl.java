package ru.itis.servletsapp.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.servletsapp.dao.UsersRepository;
import ru.itis.servletsapp.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {

    private final static String SQL_INSERT = "insert into users(first_name, last_name, age, gender, description, password_hash, email, avatar_id) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update users set first_name = ?, last_name = ?, age = ?, gender = ?, description = ?, password_hash = ?, email = ?, avatar_id = ? where id = ?";
    private final static String SQL_UPDATE_AVATAR = "update users set avatar_id = ? where id = ?";
    private final static String SQL_UPDATE_TOKEN = "update user_token set token = ? where user_id = ?";
    private final static String SQL_CREATE_TOKEN = "insert into user_token(user_id, token) VALUES (?, ?)";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?";
    private final static String SQL_SELECT_TOKEN_BY_USER_ID = "select * from user_token where user_id = ?";
    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
    private final static String SQL_SELECT_BY_TOKEN = "select * from user_token join users  on user_token.user_id = users.id where token = ?";
    private final static String SQL_SELECT_ALL = "select u.* from users u";
    private final static String SQL_SELECT_ALL_EXCEPT_ID = "select u.* from users u where u.id != ?";
    private final static String SQL_SELECT_PAIR = "select u1.* from users as u1 where u1.id NOT IN (select inter.interacted_with_id from users as u2 join interacted as inter on inter.user_id = u2.id where u2.id = ?) AND u1.id != ? limit 1";
    private final static String SQL_SELECT_MATCHES = "select m.match_id from matches m where user_id = ?";
    private final static String SQL_INSERT_MATCHES = "insert into matches(user_id, match_id) values(?,?)";
    private final static String SQL_INSERT_INTERACTED = "insert into interacted(user_id, interacted_with_id) values (?,?)";
    private final static String SQL_DELETE_TOKEN = "delete from user_token where user_id = ?";

    private final RowMapper<User> rowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getLong("id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .age(row.getInt("age"))
                    .gender(row.getString("gender"))
                    .description(row.getString("description"))
                    .hashPassword(row.getString("password_hash"))
                    .email(row.getString("email"))
                    .avatarId(row.getLong("avatar_id") == 0 ? null : row.getLong("avatar_id"))
                    .build();

    private final RowMapper<Long> longRowMapper = (row, rowNumber) -> row.getLong("match_id");

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, rowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByToken(String token) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_TOKEN, rowMapper, token));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Long> findMatchesId(Long id) {
        return jdbcTemplate.query(SQL_SELECT_MATCHES, longRowMapper,id);
    }

    @Override
    public Optional<User> getPair(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_PAIR, rowMapper, id, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteToken(Long id) {
        jdbcTemplate.update(SQL_DELETE_TOKEN,id);
    }

    @Override
    public void setMatch(Long userId, Long matchId) {
        jdbcTemplate.update(SQL_INSERT_MATCHES,userId,matchId);
    }

    @Override
    public void updateAvatarForUser(Long userId, Long fileId) {
        jdbcTemplate.update(SQL_UPDATE_AVATAR, fileId, userId);
    }

    @Override
    public Optional<String> getTokenByUserId(Long userId) {
        return jdbcTemplate.query(SQL_SELECT_TOKEN_BY_USER_ID, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(resultSet.getString("token"));
            } else {
                return Optional.empty();
            }
        }, userId);
    }

    @Override
    public void createTokenForUser(Long userId, String token) {
        jdbcTemplate.update(SQL_CREATE_TOKEN, userId, token);
    }

    @Override
    public void updateTokenForUser(Long userId, String token) {
        jdbcTemplate.update(SQL_UPDATE_TOKEN, token, userId);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public User findAll(Long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_ALL_EXCEPT_ID, rowMapper,id);
    }

    @Override
    public void setInteractedWith(Long userId, Long interactedWith) {
        jdbcTemplate.update(SQL_INSERT_INTERACTED, userId, interactedWith);
    }

    @Override
    public User save(User item) {
        if (item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setInt(3, item.getAge());
                statement.setString(4, item.getGender());
                statement.setString(5, item.getDescription());
                statement.setString(6, item.getHashPassword());
                statement.setString(7, item.getEmail());
                if (item.getAvatarId() != null) {
                    statement.setLong(8, item.getAvatarId());
                } else {
                    statement.setNull(8, Types.NULL);
                }
                return statement;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                item.setId(keyHolder.getKey().longValue());
            }
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getFirstName(),
                    item.getLastName(),
                    item.getAge(),
                    item.getGender(),
                    item.getDescription(),
                    item.getHashPassword(),
                    item.getEmail(),
                    item.getAvatarId(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Long id) {
        //TODO implement
    }
}
