package com.watch_collector.hajun.service;

import com.watch_collector.hajun.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.security.Provider;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    UserService service = new UserService();

    @AfterEach
    public void after_each(){
        service.resetAllUser();
    }

    // 회원 가입
    @Test
    public void 회원가입(){
        // Given
        User newUser = new User("temp_id", "temp_pw");
        // When
        boolean isSuccess = service.join(newUser);
        // Then
        User find = service.findUser("temp_id").orElse(null);
        Assertions.assertEquals(newUser, find);
        Assertions.assertTrue(isSuccess);
    }

    // 회원 가입 중복 처리
    @Test
    public void 회원가입_중복(){
        // Given
        User newUser1 = new User("temp_id1", "temp_pw1");
        User newUser2 = new User("temp_id2", "temp_pw2");
        // When
        service.join(newUser1);
        boolean isSuccess = service.join(newUser2);
        // Then
        Assertions.assertFalse(isSuccess);
        Assertions.assertEquals(Optional.empty(), service.findUser("temp_id2"));
    }

    // 회원 탈퇴
    @Test
    public void 회원탈퇴(){
        // Given
        service.join(new User("temp_id", "temp_pw"));
        // When
        User find = service.findUser("temp_id").orElse(null);
        boolean isSuccess = service.withdraw(find);
        // Then
        Assertions.assertTrue(isSuccess);
        Assertions.assertNull(service.findUser("temp_id").orElse(null));
    }

    // 회원 탈퇴 없는 아이디
    @Test
    public void 회원탈퇴_조회불가(){
        // Given
        User withdraw = new User("temp_id", "temp_pw");
        // When
        boolean isSuccess = service.withdraw(withdraw);
        // Then
        Assertions.assertFalse(isSuccess);
        Assertions.assertEquals(Optional.empty(), service.findUser("temp_id"));
    }

    // 회원 조회
    @Test
    public void 회원조회(){
        // Given
        User user = new User("temp_id", "temp_pw");
        service.join(user);
        // When
        Optional<User> op = service.findUser("temp_id");
        // Then
        Assertions.assertEquals(user, op.orElse(null));
    }

    // 회원 조회 없는 아이디
    @Test
    public void 회원조회_조회불가(){
        // Given
        // When
        Optional<User> op = service.findUser("temp_id");
        // Then
        Assertions.assertNull(op.orElse(null));
    }

    // 전체 회원 리스트
    @Test
    public void 회원목록조회(){
        // Given
        service.join(new User("temp_id1", "temp_pw1"));
        service.join(new User("temp_id2", "temp_pw2"));
        // When
        List<User> list = service.findAllUser();
        // Then
        Assertions.assertEquals(2, list.size());
    }
}
