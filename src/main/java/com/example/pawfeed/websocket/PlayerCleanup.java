package com.example.pawfeed.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class CleanupScheduler {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedDelay = 15000) // runs every 15 seconds
    public void cleanup() {
        int before = GameState.players.size();
        GameState.cleanupInactivePlayers();
        int after = GameState.players.size();

        // Only broadcast updated player list if someone was removed
        if (before != after) {
            System.out.println("Cleaned up " + (before - after) + " inactive player(s)");
            messagingTemplate.convertAndSend(
                "/topic/players",
                new ArrayList<>(GameState.players)
            );
        }
    }
}
