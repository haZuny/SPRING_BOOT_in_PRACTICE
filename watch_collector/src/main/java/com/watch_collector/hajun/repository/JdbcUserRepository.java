package com.watch_collector.hajun.repository;

import com.watch_collector.hajun.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User addUser(User user) {
        String query = "INSERT INTO USER(ID, PW) VALUES (?, ?);";
        jdbcTemplate.update(query, user.getId(), user.getPw());
        return user;
    }

    @Override
    public Optional<User> deleteById(String id) {
        String query = "DELETE FROM USER WHERE id=?;";
        jdbcTemplate.update(query, id);
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        String query = "SELECT * FROM USER WHERE id=?;";
        List<User> result = jdbcTemplate.query(query, userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM USER;";
        return jdbcTemplate.query(query, userRowMapper());
    }

    @Override
    public void removeAllUser() {
        String query = "TRUNCATE TABLE user;";
        jdbcTemplate.update(query);
    }

    RowMapper<User> userRowMapper() {
        return (rs, rowNum)->{
            User user = new User("", "");
            user.setId(rs.getString("id"));
            user.setPw(rs.getString("pw"));
            return user;
        };
    }
}
