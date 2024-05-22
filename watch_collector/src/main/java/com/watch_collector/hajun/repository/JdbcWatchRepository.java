package com.watch_collector.hajun.repository;

import com.watch_collector.hajun.domain.Watch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcWatchRepository implements WatchRepository{
    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcWatchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Watch addWatch(Watch watch) {
        String query = "insert into watch (user_id, model, case_size, movement, lug_to_lug, glass) values (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement psmt = con.prepareStatement(query, new String[]{"id"});
            psmt.setString(1, watch.getUserId());
            psmt.setString(2, watch.getModel());
            psmt.setInt(3, watch.getCaseSize());
            psmt.setString(4, watch.getMovement());
            psmt.setInt(5, watch.getLugToLug());
            psmt.setString(6, watch.getGlass());
            return psmt;
        }, keyHolder);
        watch.setId(keyHolder.getKey().intValue());
        return watch;
    }

    @Override
    public Optional<Watch> findById(int id) {
        String query = "select * from watch where id=?;";
        List<Watch> result = jdbcTemplate.query(query, watchRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Watch> findByUser(String userId) {
        String query = "select * from watch where user_id=?;";
        return jdbcTemplate.query(query, watchRowMapper(), userId);
    }

    @Override
    public List<Watch> getAllWatches() {
        String query = "select * from watch;";
        return jdbcTemplate.query(query, watchRowMapper());
    }

    @Override
    public Optional<Watch> updateWatch(Watch watch) {
        String query = "update watch set model=?, case_size=?, movement=?, lug_to_lug=?, glass=? where id=?;";
        jdbcTemplate.update(query, watch.getModel(), watch.getCaseSize(),
                watch.getMovement(), watch.getLugToLug(), watch.getGlass(), watch.getId());
        return Optional.empty();
    }

    @Override
    public boolean deleteWatch(Watch watch) {
        String query = "delete from watch where id=?;";
        jdbcTemplate.update(query, watch.getId());
        return true;
    }

    @Override
    public boolean deleteByUser(String userId) {
        String query = "delete from watch where user_id=?;";
        jdbcTemplate.update(query, userId);
        return true;
    }

    @Override
    public boolean removeAllWatches() {
        return false;
    }

    RowMapper<Watch> watchRowMapper(){
        return (rs, rowNum)->{
            Watch watch = new Watch(rs.getString("user_id"), rs.getString("model"), rs.getInt("case_size"),
                    rs.getString("movement"), rs.getInt("lug_to_lug"),
                    rs.getString("glass"), new ArrayList<>());
            watch.setId(rs.getInt("id"));
            return watch;
        };
    }
}
