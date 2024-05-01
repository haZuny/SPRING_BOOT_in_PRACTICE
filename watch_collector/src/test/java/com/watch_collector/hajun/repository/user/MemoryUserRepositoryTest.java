package com.watch_collector.hajun.repository.user;

import com.watch_collector.hajun.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryUserRepositoryTest {

    // MemoryUserRepository
    UserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void 레포초기화(){
        repository.removeAllUser();
    }

    @Test
    public void 회원추가(){
        // Given
        User user = new User("new_id", "1234");

        // When
        repository.addUser(user);

        // Then
        Assertions.assertEquals(user, repository.findById("new_id").get());

    }

    @Test
    public void 회원삭제_id(){
        // Given
        String id = "new_id";
        User user = new User(id, "1234");
        repository.addUser(user);

        // When
        User deleted = repository.deleteById(id).get();

        // Then
        Assertions.assertEquals(user, deleted);
    }

    @Test
    public void 회원검색_id(){
        // Given
        String id = "new_id";
        User user = new User(id, "1234");
        repository.addUser(user);

        // When
        User searched = repository.findById(id).get();

        // Then
        Assertions.assertEquals(user, searched);
    }

    @Test
    public void 회원목록조회(){
        // Given
        User user1 = new User("user1", "123");
        User user2 = new User("user2", "456");
        User user3 = new User("user3", "789");
        repository.addUser(user1);
        repository.addUser(user2);
        repository.addUser(user3);

        // When
        List<User> list = repository.findAll();

        // Then
        Assertions.assertEquals(3, list.size());
    }
}
