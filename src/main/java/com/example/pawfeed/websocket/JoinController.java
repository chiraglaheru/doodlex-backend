package com.example.pawfeed.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JoinController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/join")
    @SendTo("/topic/players")
    public List<String> join(String userId) {
        GameState.addPlayer(userId);
        GameState.updateActivity(userId);
        GameState.debug();

        // Tell only the joining player who the current drawer is
        // so it doesn't broadcast a drawer-reset to everyone
        String currentDrawer = GameState.getCurrentDrawer();
        if (currentDrawer != null) {
            messagingTemplate.convertAndSend(
                "/topic/turn/" + userId, currentDrawer
            );
        }

        return new ArrayList<>(GameState.players);
    }

    @MessageMapping("/syncTurn")
    @SendTo("/topic/turn")
    public String syncTurn() {
        return GameState.getCurrentDrawer();
    }
}
