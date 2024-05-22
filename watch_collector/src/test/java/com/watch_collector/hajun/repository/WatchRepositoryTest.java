package com.watch_collector.hajun.repository;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
public class WatchRepositoryTest {
    WatchRepository repository;

    @Autowired
    public WatchRepositoryTest(JdbcWatchRepository repository) {
        this.repository = repository;
    }

    Watch generateWatch(String userId){
        return new Watch(userId, "model", 38, "auto", 44, "Mineral", new ArrayList<>());
    }

    @AfterEach
    public void after_each(){
        repository.removeAllWatches();
    }

    // 시계 추가
    @Test
    public void 시계추가(){
        // Given
        Watch watch = generateWatch("1234");
        // When
        Watch added = repository.addWatch(watch);
        // Then
        Assertions.assertNotNull(added);
        Assertions.assertEquals(watch, added);
    }

    // id로 시계 찾기
    @Test
    public void 시계찾기_id(){
        // Given
        Watch watch = repository.addWatch(generateWatch("1234"));
        int watchId = watch.getId();
        // When
        Watch searched = repository.findById(watchId).orElse(null);
        // Then
        Assertions.assertNotNull(searched);
        Assertions.assertEquals(watch, searched);
    }

    // 특정 User의 시계 목록 조회
    @Test
    public void 시계목록조회_USer(){
        // Given
        String userId1 = "1234";
        String userId2 = "temp_id";
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId2));
        repository.addWatch(generateWatch(userId2));
        // When
        List<Watch> list1 = repository.findByUser(userId1);
        List<Watch> list2 = repository.findByUser(userId2);
        // Then
        Assertions.assertEquals(3, list1.size());
        Assertions.assertEquals(2, list2.size());
    }

    // 모든 시계 목록 반환
    @Test
    public void 모든시계목록(){
        // Given
        repository.addWatch(generateWatch("1234"));
        repository.addWatch(generateWatch("1234"));
        repository.addWatch(generateWatch("temp_id"));
        repository.addWatch(generateWatch("temp_id"));
        repository.addWatch(generateWatch("temp_id1"));
        // When
        List<Watch> list = repository.getAllWatches();
        // Then
        Assertions.assertEquals(5, list.size());
    }

    // 시계 정보 변경
    @Test
    public void 시계정보변경(){
        // Given
        Watch watch = new Watch("user1", "model", 38, "auto", 44, "Mineral", new ArrayList<>());
        watch = repository.addWatch(watch);
        // When
        watch.setModel("model2");
        watch.setCaseSize(40);
        Watch updated = repository.updateWatch(watch).orElse(null);
        // Then
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(watch, updated);
        Assertions.assertEquals(watch.getId(), updated.getId());
    }

    // 시계 삭제
    @Test
    public void 시계삭제(){
        // Given
        Watch watch1 = generateWatch("1234");
        Watch watch2 = generateWatch("1234");
        watch1 = repository.addWatch(watch1);
        watch2 = repository.addWatch(watch2);
        // When
        boolean result = repository.deleteWatch(watch1);
        // Then
        Assertions.assertTrue(result);
        Assertions.assertEquals(1, repository.getAllWatches().size());
        Assertions.assertNull(repository.findById(watch1.getId()).orElse(null));
    }
    // 특정 User의 시계들 제거
    @Test
    public void 시계삭제_User(){
        // Given
        String userId1 = "1234";
        String userId2 = "temp_id";
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId2));
        repository.addWatch(generateWatch(userId2));
        // When
        boolean result = repository.deleteByUser(userId1);
        // Then
        Assertions.assertTrue(result);
        Assertions.assertEquals(2, repository.getAllWatches().size());
        Assertions.assertEquals(0, repository.findByUser(userId1).size());

    }

    // 모든 초기화
    @Test
    public void 모든시계제거(){
        // Given
        String userId1 = "1234";
        String userId2 = "temp_id";
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId1));
        repository.addWatch(generateWatch(userId2));
        repository.addWatch(generateWatch(userId2));
        // When
        boolean result = repository.removeAllWatches();
        // Then
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, repository.getAllWatches().size());
    }
}
