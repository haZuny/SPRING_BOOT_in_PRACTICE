package com.watch_collector.hajun.repository.userRepository;

import com.watch_collector.hajun.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    // HashMap을 사용하여 데이터 저장
    private static final Map<String, User> store= new HashMap<String, User>();

    @Override
    public User addUser(User user) {
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> deleteById(String id) {
        Optional<User> optional = Optional.empty();
        try {
            optional = Optional.of(store.get(id));
            store.remove(id);
        } catch (Exception e){}
        return optional;
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> optional = Optional.empty();
        try {
            optional = Optional.of(store.get(id));
        } catch (Exception e){}
        return optional;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
