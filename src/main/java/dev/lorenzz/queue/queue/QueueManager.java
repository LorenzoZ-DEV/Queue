package dev.lorenzz.queue.queue;

import dev.lorenzz.queue.managment.Manager;
import lombok.Getter;

import java.util.*;

public class QueueManager implements Manager {

    @Getter
    private final Map<String, Deque<UUID>> queues = new HashMap<>();
    private final Map<String, Boolean> paused = new HashMap<>();

    @Override
    public void start() {}

    @Override
    public void stop() {
        queues.clear();
        paused.clear();
    }

    // queue operations
    public boolean enqueue(String target, UUID playerId) {
        Deque<UUID> q = queues.computeIfAbsent(target.toLowerCase(Locale.ROOT), k -> new ArrayDeque<>());
        if (q.contains(playerId)) return false;
        q.addLast(playerId);
        return true;
    }

    public boolean dequeue(String target, UUID playerId) {
        Deque<UUID> q = queues.get(target.toLowerCase(Locale.ROOT));
        if (q == null) return false;
        return q.remove(playerId);
    }

    public Optional<UUID> peek(String target) {
        Deque<UUID> q = queues.get(target.toLowerCase(Locale.ROOT));
        if (q == null) return Optional.empty();
        return Optional.ofNullable(q.peekFirst());
    }

    public boolean isQueued(UUID playerId) {
        return queues.values().stream().anyMatch(q -> q.contains(playerId));
    }

    public Optional<String> queueOf(UUID playerId) {
        return queues.entrySet().stream()
                .filter(e -> e.getValue().contains(playerId))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public int position(String target, UUID playerId) {
        Deque<UUID> q = queues.get(target.toLowerCase(Locale.ROOT));
        if (q == null) return -1;
        int idx = 1;
        for (UUID id : q) {
            if (id.equals(playerId)) return idx;
            idx++;
        }
        return -1;
    }

    public boolean togglePause(String target) {
        String key = target.toLowerCase(Locale.ROOT);
        boolean newState = !isPaused(key);
        paused.put(key, newState);
        return newState;
    }

    public boolean isPaused(String target) {
        return paused.getOrDefault(target.toLowerCase(Locale.ROOT), false);
    }
}
