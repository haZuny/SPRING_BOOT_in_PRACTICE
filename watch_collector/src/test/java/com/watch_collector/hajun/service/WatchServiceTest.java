package com.watch_collector.hajun.service;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WatchServiceTest {
    WatchService watchService;

    public WatchServiceTest(WatchService watchService) {
        this.watchService = watchService;
    }

    Watch generateWatch(){
        return new Watch("model", 38, "auto", 44, "Mineral", new ArrayList<>());
    }

    @AfterEach
    public void after_each(){
        watchService.deleteAllWatches();
    }

    // 시계 추가
    @Test
    public void 시계추가(){
        // Given
        User user = new User("user1", "1234");
        Watch watch = generateWatch();
        // When
        Watch added = watchService.addWatch(watch, user);
        // Then
        Assertions.assertSame(added, watchService.findById(added.getId()).orElse(null));
    }

    // 시계 찾기
    @Test
    public void 시계찾기_id(){
        // Given
        User user = new User("user1", "1234");
        Watch watch = generateWatch();
        watch = watchService.addWatch(watch, user);
        // When
        Watch found = watchService.findById(watch.getId()).orElse(null);
        // Then
        Assertions.assertNotNull(found);
        Assertions.assertEquals(watch, found);
    }
    public void 시계찾기_id_존재하지않는아이디(){
        // Given
        User user = new User("user1", "1234");
        Watch watch = generateWatch();
        watch = watchService.addWatch(watch, user);
        // When
        Watch found = watchService.findById(-1).orElse(null);
        // Then
        Assertions.assertNull(found);
    }

    // 한 사용자의 시계 목록 조회
    @Test
    public void 시계찾기_User(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user2);
        watchService.addWatch(generateWatch(), user2);
        // When
        List<Watch> found_user1 = watchService.watchesByUser(user1);
        List<Watch> found_user2 = watchService.watchesByUser(user2);
        // Then
        Assertions.assertEquals(3, found_user1.size());
        Assertions.assertEquals(2, found_user2.size());
    }

    // 전체 시계 목록 조회
    @Test
    public void 시계찾기_All(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user2);
        watchService.addWatch(generateWatch(), user2);
        // When
        List<Watch> list = watchService.getAllWatches();
        // Then
        Assertions.assertEquals(5, list.size());
    }

    // 시계 정보 변경하기
    @Test
    public void 시계정보변경(){
        // Given
        User user = new User("user1", "1234");
        Watch watch = generateWatch();
        watch = watchService.addWatch(watch, user);
        // When
        Watch changeWatch = watchService.findById(watch.getId()).orElse(null);
        changeWatch.setCaseSize(30);
        Watch changed = watchService.updateWatch(changeWatch).orElse(null);
        // Then
        Assertions.assertEquals(changeWatch, changed);
    }

    @Test
    public void 시계정보변경_해당시계없는경우(){
        // Given
        User user = new User("user1", "1234");
        Watch watch = generateWatch();
        watch = watchService.addWatch(watch, user);
        // When
        Watch changeWatch = generateWatch();
        changeWatch.setCaseSize(30);
        Watch changed = watchService.updateWatch(changeWatch).orElse(null);
        // Then
        Assertions.assertNull(changed);
    }

    // 시계 삭제하기
    @Test
    public void 시계제거(){
        // Given
        User user = new User("user1", "1234");
        Watch watch = generateWatch();
        watch = watchService.addWatch(watch, user);
        // When
        boolean result = watchService.deleteWatch(watch);
        // Then
        Assertions.assertTrue(result);
        Assertions.assertNull(watchService.findById(watch.getId()).orElse(null));
        Assertions.assertEquals(0, watchService.getAllWatches().size());
    }

    @Test
    public void 시계제거_해당시계없는경우(){
        // Given
        User user = new User("user1", "1234");
        Watch watch = generateWatch();
        watch = watchService.addWatch(watch, user);
        // When
        Watch deleteWatch = generateWatch();
        boolean result = watchService.deleteWatch(deleteWatch);
        // Then
        Assertions.assertFalse(result);
        Assertions.assertNotNull(watchService.findById(watch.getId()));
        Assertions.assertEquals(1, watchService.getAllWatches().size());
    }

    @Test
    public void 시계제거_사용자(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user2);
        watchService.addWatch(generateWatch(), user2);
        // When
        boolean result = watchService.deleteUserWatch(user1);
        // Then
        Assertions.assertTrue(result);
        Assertions.assertEquals(2, watchService.getAllWatches().size());
        Assertions.assertEquals(0, watchService.watchesByUser(user1).size());
    }

    @Test
    public void 시계제거_사용자_해당사용자없음(){
        // Given
        User user1 = new User("user1", "1234");
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        // When
        User user2 = new User("user2", "1234");
        boolean result = watchService.deleteUserWatch(user2);
        // Then
        Assertions.assertEquals(3, watchService.getAllWatches().size());
    }

    @Test
    public void 시계제거_All(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user1);
        watchService.addWatch(generateWatch(), user2);
        watchService.addWatch(generateWatch(), user2);
        // When
        boolean result = watchService.deleteAllWatches();
        // Then
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, watchService.getAllWatches().size());
    }
}
