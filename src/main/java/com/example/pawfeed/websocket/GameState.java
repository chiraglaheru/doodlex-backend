package com.example.pawfeed.websocket;

import java.util.*;

public class GameState {

    public static List<String> players =
            Collections.synchronizedList(new ArrayList<>());

    public static int currentDrawerIndex = 0;

    public static Map<String, Integer> scores =
            Collections.synchronizedMap(new HashMap<>());

    public static String currentWord;

    public static synchronized void reset() {
        players.clear();
        scores.clear();
        currentDrawerIndex = 0;
    }

    public static synchronized void addPlayer(String userId) {

        if (!players.contains(userId)) {
            players.add(userId);
            scores.put(userId, 0);
        }

        if (currentDrawerIndex >= players.size()) {
            currentDrawerIndex = 0;
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
        players.remove(userId);
        scores.remove(userId);

        if (currentDrawerIndex >= players.size()) {
            currentDrawerIndex = 0;
        }
    }

    public static synchronized void debug() {
        System.out.println("PLAYERS: " + players);
        System.out.println("DRAWER: " + getCurrentDrawer());
    }
}