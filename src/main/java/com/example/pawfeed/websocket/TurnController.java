package com.example.pawfeed.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TurnController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Removed @SendTo — we handle it manually now
    @MessageMapping("/getTurn")
    public void getTurn() {
        // Does nothing — joining players get turn info
        // via JoinController's convertAndSend now
        // Keeping endpoint alive so frontend doesn't error
    }

    @MessageMapping("/nextTurn")
    @SendTo("/topic/turn")
    public String nextTurn() {
        GameState.nextTurn();
        GameState.gameInProgress = true;
        return GameState.getCurrentDrawer();
    }
}
