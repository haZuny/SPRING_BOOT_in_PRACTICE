package com.watch_collector.hajun.service;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import org.junit.jupiter.api.Assertions;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WatchServiceTest {
    WatchService watchService = new WatchService();

    Watch generateWatch(String userId){
        return new Watch(userId, "model", 38, "auto", 44, "Mineral", new ArrayList<>());
    }

    // 시계 추가
    public void 시계추가(){
        // Given
        Watch watch = generateWatch("user1");
        // When
        Watch added = watchService.addWatch(watch);
        // Then
        Assertions.assertSame(added, watchService.findById(added.getId()).orElse(null));
    }

    // 시계 찾기
    public void 시계찾기_id(){
        // Given
        Watch watch = generateWatch("user1");
        watch = watchService.addWatch(watch);
        // When
        Watch found = watchService.findById(watch.getId()).orElse(null);
        // Then
        Assertions.assertNotNull(found);
        Assertions.assertEquals(watch, found);
    }

    // 한 사용자의 시계 목록 조회
    public void 시계찾기_User(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        // When
        List<Watch> found_user1 = watchService.watchesByUser(user1);
        List<Watch> found_user2 = watchService.watchesByUser(user1);
        // Then
        Assertions.assertEquals(3, found_user1.size());
        Assertions.assertEquals(2, found_user2.size());
    }

    // 전체 시계 목록 조회
    public void 시계찾기_All(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        // When
        List<Watch> list = watchService.getAllWatches();
        // Then
        Assertions.assertEquals(5, list.size());
    }

    // 시계 정보 변경하기
    public void 시계정보변경(){
        // Given
        Watch watch = generateWatch("user1");
        watch = watchService.addWatch(watch);
        // When
        Watch changeWatch = watchService.findById(watch.getId()).orElse(null);
        changeWatch.setCaseSize(30);
        Watch changed = watchService.updateWatch(changeWatch).orElse(null);
        // Then
        Assertions.assertEquals(changeWatch, changed);
    }
    public void 시계정보변경_해당시계없는경우(){
        // Given
        Watch watch = generateWatch("user1");
        watch = watchService.addWatch(watch);
        // When
        Watch changeWatch = generateWatch("user2");
        changeWatch.setCaseSize(30);
        Watch changed = watchService.updateWatch(changeWatch).orElse(null);
        // Then
        Assertions.assertNull(changed);
    }

    // 시계 삭제하기
    public void 시계제거(){
        // Given
        Watch watch = generateWatch("user1");
        watch = watchService.addWatch(watch);
        // When
        boolean result = watchService.deleteWatch(watch);
        // Then
        Assertions.assertTrue(result);
        Assertions.assertNull(watchService.findById(watch.getId()));
        Assertions.assertEquals(0, watchService.getAllWatches().size());
    }
    public void 시계제거_해당시계없는경우(){
        // Given
        Watch watch = generateWatch("user1");
        watch = watchService.addWatch(watch);
        // When
        Watch deleteWatch = generateWatch("user2");
        boolean result = watchService.deleteWatch(deleteWatch);
        // Then
        Assertions.assertFalse(result);
        Assertions.assertNotNull(watchService.findById(watch.getId()));
        Assertions.assertEquals(1, watchService.getAllWatches().size());
    }
    public void 시계제거_사용자(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        // When
        boolean result = watchService.deleteUserWatch(user1);
        // Then
        Assertions.assertTrue(result);
        Assertions.assertEquals(2, watchService.getAllWatches().size());
        Assertions.assertEquals(0, watchService.watchesByUser(user1).size());
    }
    public void 시계제거_사용자_해당사용자없음(){
        // Given
        User user1 = new User("user1", "1234");
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        // When
        User user2 = new User("user2", "1234");
        boolean result = watchService.deleteUserWatch(user2);
        // Then
        Assertions.assertEquals(3, watchService.getAllWatches().size());
    }
    public void 시계제거_All(){
        // Given
        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user1.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        watchService.addWatch(generateWatch(user2.getId()));
        // When
        boolean result = watchService.deleteAllWatches();
        // Then
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, watchService.getAllWatches().size());
    }
}
