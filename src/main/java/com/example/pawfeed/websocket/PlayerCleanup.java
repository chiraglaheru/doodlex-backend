package com.example.pawfeed.websocket;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PlayerCleanup {

    @Scheduled(fixedRate = 5000) // every 5 sec
    public void cleanup() {
        GameState.cleanupInactivePlayers();
    }
}
