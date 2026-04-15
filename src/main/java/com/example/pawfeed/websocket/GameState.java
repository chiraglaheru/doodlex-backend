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

    public static synchronized void reset() {
        players.clear();
        scores.clear();
        lastSeen.clear();
        currentDrawerIndex = 0;
    }

    public static synchronized void addPlayer(String userId) {

        if (!players.contains(userId)) {
            players.add(userId);
            scores.put(userId, 0);
        }

        lastSeen.put(userId, System.currentTimeMillis());

        if (currentDrawerIndex >= players.size()) {
            currentDrawerIndex = 0;
        }
    }

    public static synchronized void updateActivity(String userId) {
        lastSeen.put(userId, System.currentTimeMillis());
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

        players.remove(userId);
        scores.remove(userId);
        lastSeen.remove(userId);

        if (removedIndex != -1 && removedIndex <= currentDrawerIndex) {
            currentDrawerIndex--;
        }

        if (currentDrawerIndex < 0) {
            currentDrawerIndex = 0;
        }

        if (currentDrawerIndex >= players.size()) {
            currentDrawerIndex = 0;
        }
    }

    public static synchronized void cleanupInactivePlayers() {

        long now = System.currentTimeMillis();
        List<String> toRemove = new ArrayList<>();

        for (String player : players) {
            Long last = lastSeen.get(player);

            if (last == null || (now - last) > 10000) { // 10 sec timeout
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
        System.out.println("DRAWER: " + getCurrentDrawer());
    }
}
