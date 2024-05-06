package com.watch_collector.hajun.repository;

import com.watch_collector.hajun.domain.Watch;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryWatchRepository implements WatchRepository {
    Map<Integer, Watch> store = new HashMap<>();
    int id_autoIncreased = 0;

    @Override
    public Watch addWatch(Watch watch) {
        watch.setId(id_autoIncreased);
        store.put(id_autoIncreased++, watch);
        return watch;
    }

    @Override
    public Optional<Watch> findById(int id) {
        if (store.containsKey(id)){
            return Optional.ofNullable(store.get(id));
        }
        else return Optional.empty();
    }

    @Override
    public List<Watch> findByUser(String userId) {
        List<Watch> list = new ArrayList<>();
        for (Watch watch : store.values()) {
            if (watch.getUserId().equals(userId)) list.add(watch);
        }
        return list;
    }

    @Override
    public List<Watch> getAllWatches() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Watch> updateWatch(Watch watch) {
        if (store.containsKey(watch.getId())){
            store.put(watch.getId(), watch);
            return Optional.of(watch);
        }
        else return Optional.empty();
    }

    @Override
    public boolean deleteWatch(Watch watch) {
        if (store.containsKey(watch.getId())){
            store.remove(watch.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByUser(String userId) {
        for (int id : new ArrayList<Integer>(store.keySet())){
            if (store.get(id).getUserId().equals(userId))   store.remove(id);
        }
        return true;
    }

    @Override
    public boolean removeAllWatches() {
        store.clear();
        return true;
    }
}
