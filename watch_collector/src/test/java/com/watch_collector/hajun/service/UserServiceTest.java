package com.watch_collector.hajun.service;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.security.Provider;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    UserService service;
    WatchService watchService;

    @Autowired
    public UserServiceTest(UserService userService, WatchService watchService) {
        this.service = userService;
        this.watchService = watchService;
    }

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
        User newUser1 = new User("temp_id", "temp_pw1");
        User newUser2 = new User("temp_id", "temp_pw2");
        // When
        service.join(newUser1);
        boolean isSuccess = service.join(newUser2);
        // Then
        Assertions.assertFalse(isSuccess);
        Assertions.assertEquals(Optional.empty(), service.findUser("temp_id2"));
    }
    @Test
    public void 회원가입_공백(){
        // Given
        User user1 = new User("", "1234");
        User user2 = new User("user2", "");
        // When
        boolean res1 = service.join(user1);
        boolean res2 = service.join(user2);
        // Then
        Assertions.assertFalse(res1);
        Assertions.assertFalse(res2);
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
        Assertions.assertEquals(0, service.watchesByUser(find).size());
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

    // 유효성 검증
    @Test
    public void 유효성검증_일치(){
        // Given
        String id = "user1";
        String pw = "1234";
        User user = new User(id, pw);
        Assertions.assertTrue(service.join(user));
        // When
        boolean result = service.checkIdPw(id, pw);
        // Then
        Assertions.assertTrue(result);
    }
    @Test
    public void 유효성검증_불일치(){
        // Given
        String id = "user1";
        String pw = "1234";
        User user = new User(id, pw);
        Assertions.assertTrue(service.join(user));
        // When
        String falsePw = "2345";
        boolean result = service.checkIdPw(id, falsePw);
        // Then
        Assertions.assertFalse(result);
    }
    @Test
    public void 유효성검증_없는아이디(){
        // Given
        String id = "user1";
        String pw = "1234";
        User user = new User(id, pw);
        Assertions.assertTrue(service.join(user));
        // When
        String falseId = "1234";
        boolean result = service.checkIdPw(falseId, pw);
        // Then
        Assertions.assertFalse(result);
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
