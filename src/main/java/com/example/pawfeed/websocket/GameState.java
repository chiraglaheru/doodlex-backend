package com.example.pawfeed.websocket;
import java.util.*;

public class GameState {
    public static List<String> players =
            Collections.synchronizedList(new ArrayList<>());
    public static Map<String, Integer> scores =
            Collections.synchronizedMap(new HashMap<>());
    public static Map<String, Long> lastSeen =
            Collections.synchronizedMap(new HashMap<>());
    public static int currentDrawerIndex = 0;
    public static String currentWord;
    public static boolean gameInProgress = false;

    public static synchronized void reset() {
        players.clear();
        scores.clear();
        lastSeen.clear();
        currentDrawerIndex = 0;
        gameInProgress = false;
    }

    public static synchronized void addPlayer(String userId) {
        if (!players.contains(userId)) {
            players.add(userId);
            scores.put(userId, 0);
            // Don't touch currentDrawerIndex if game is running
            if (!gameInProgress && currentDrawerIndex >= players.size()) {
                currentDrawerIndex = 0;
            }
        }
        lastSeen.put(userId, System.currentTimeMillis());
    }

    public static synchronized void updateActivity(String userId) {
        if (players.contains(userId)) {
            lastSeen.put(userId, System.currentTimeMillis());
        }
    }

    public static synchronized String getCurrentDrawer() {
        if (players.isEmpty()) return null;
        if (currentDrawerIndex >= players.size()) {
            currentDrawerIndex = 0;
        }
        return players.get(currentDrawerIndex);
    }

    public static synchronized void nextTurn() {
        if (players.isEmpty()) return;
        currentDrawerIndex =
                (currentDrawerIndex + 1) % players.size();
    }

    public static synchronized void removePlayer(String userId) {
        int removedIndex = players.indexOf(userId);
        if (removedIndex == -1) return; // player not found, do nothing

        players.remove(userId);
        scores.remove(userId);
        lastSeen.remove(userId);

        if (players.isEmpty()) {
            currentDrawerIndex = 0;
            gameInProgress = false;
            return;
        }

        if (removedIndex < currentDrawerIndex) {
            currentDrawerIndex--;
        } else if (removedIndex == currentDrawerIndex) {
            // The drawer left — stay at same index (points to next player now)
            if (currentDrawerIndex >= players.size()) {
                currentDrawerIndex = 0;
            }
        }
    }

    public static synchronized void cleanupInactivePlayers() {
        long now = System.currentTimeMillis();
        List<String> toRemove = new ArrayList<>();
        for (String player : players) {
            Long last = lastSeen.get(player);
            if (last == null || (now - last) > 120000) { // 2 min timeout
                toRemove.add(player);
            }
        }
        for (String player : toRemove) {
            System.out.println("Removing inactive: " + player);
            removePlayer(player);
        }
    }

    public static synchronized void debug() {
        System.out.println("PLAYERS: " + players);
        System.out.println("DRAWER INDEX: " + currentDrawerIndex);
        System.out.println("DRAWER: " + getCurrentDrawer());
        System.out.println("GAME IN PROGRESS: " + gameInProgress);
    }
}
